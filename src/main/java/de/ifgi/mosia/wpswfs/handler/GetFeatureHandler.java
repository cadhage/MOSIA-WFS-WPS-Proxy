/**
 * Copyright (C) 2014
 * by Institute for Geoinformatics, University of Muenster
 *
 * Contact: http://www.ifgi.de
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.ifgi.mosia.wpswfs.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.opengis.wfs.x20.FeatureCollectionDocument;
import net.opengis.wfs.x20.FeatureCollectionType;
import net.opengis.wfs.x20.MemberPropertyType;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3.x2003.x05.soapEnvelope.EnvelopeDocument;

import com.google.inject.Inject;

import aero.aixm.schema.x51.RouteSegmentType;
import de.ifgi.mosia.wpswfs.ServiceException;
import de.ifgi.mosia.wpswfs.wps.WPSConnector;

public class GetFeatureHandler extends GenericRequestHandler {

	private static final Logger logger = LoggerFactory.getLogger(GetFeatureHandler.class);
	XmlOptions PRETTY_PRINT = new XmlOptions().setSavePrettyPrint();
	
	@Inject
	private WPSConnector wps;
	
	@Override
	public boolean supportsRequestType(String request) {
		return "GetFeature".equalsIgnoreCase(request);
	}

	@Override
	protected HttpResponse handleGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServiceException {
		HttpResponse response;
		try {
			response = executeGetFeature(req.getParameterMap());
		}
		catch (IOException e) {
			logger.warn(e.getMessage(), e);
			throw new IOException("Proxy server issue: "+e.getMessage());
		}
		
		if (response.getStatusLine().getStatusCode() > HttpStatus.SC_MULTIPLE_CHOICES) {
			throw new IOException("Proxy server issue. HTTP Status "+response.getStatusLine().getStatusCode());
		}
		else {
			return postProcessFeatures(response);
		}
	}
	

	@Override
	protected HttpResponse handlePost(String content, String enc, HttpServletResponse resp)
			throws IOException, ServiceException {
		HttpResponse result = super.handlePost(content, enc, resp);
		return postProcessFeatures(result);
	}

	private HttpResponse postProcessFeatures(HttpResponse response) throws IOException, ServiceException {
		BasicHttpResponse result = new BasicHttpResponse(response.getStatusLine(), null, response.getLocale());
		
		XmlObject xo;
		try {
			xo = XmlObject.Factory.parse(response.getEntity().getContent());
		} catch (IllegalStateException e) {
			throw new IOException(e);
		} catch (XmlException e) {
			throw new IOException(e);
		}
		
		List<XmlObject> processCollection = null;
		FeatureCollectionType originalCollection = null;
		/*
		 * soap stuff
		 */
		if (xo instanceof EnvelopeDocument) {
			XmlCursor bodyCur = ((EnvelopeDocument) xo).getEnvelope().getBody().newCursor();
			XmlObject obj = bodyCur.getObject();
			
			XmlObject feat = obj.newCursor().getObject().selectChildren(FeatureCollectionDocument.type.getDocumentElementName())[0];
			
			if (feat instanceof FeatureCollectionType) {
				originalCollection = (FeatureCollectionType) obj;
				processCollection = processFeatureCollection(originalCollection);	
			}
			
		}
		else if (xo instanceof org.xmlsoap.schemas.soap.envelope.EnvelopeDocument) {
			XmlCursor bodyCur = ((org.xmlsoap.schemas.soap.envelope.EnvelopeDocument) xo).getEnvelope().getBody().newCursor();
			XmlObject obj = bodyCur.getObject();
			
			XmlObject feat = obj.newCursor().getObject().selectChildren(FeatureCollectionDocument.type.getDocumentElementName())[0];
			
			if (feat instanceof FeatureCollectionType) {
				originalCollection = (FeatureCollectionType) feat;
				processCollection = processFeatureCollection(originalCollection);	
			}
			
		}
		else if (xo instanceof FeatureCollectionDocument) {
			originalCollection = ((FeatureCollectionDocument) xo).getFeatureCollection();
			processCollection = processFeatureCollection(originalCollection);
		}
		
		/*
		 * TODO: wrap with SOAP if required
		 */
		if (processCollection != null && originalCollection != null) {
			FeatureCollectionDocument featureColl = createFeatureCollectionResponse(processCollection, originalCollection);
			
			String content;
			if (xo instanceof org.xmlsoap.schemas.soap.envelope.EnvelopeDocument) {
				org.xmlsoap.schemas.soap.envelope.EnvelopeDocument env = (org.xmlsoap.schemas.soap.envelope.EnvelopeDocument) xo;
				env.getEnvelope().getBody().set(featureColl);
				content = xo.xmlText(PRETTY_PRINT);
			}
			else if (xo instanceof EnvelopeDocument) {
				EnvelopeDocument env = (EnvelopeDocument) xo;
				env.getEnvelope().getBody().set(featureColl);
				content = xo.xmlText(PRETTY_PRINT);
			}
			else {
				content = featureColl.xmlText(PRETTY_PRINT);
			}
			
			StringEntity se = new StringEntity(content);
			se.setContentType(response.getEntity().getContentType());
			result.setEntity(se);
		}
		else {
			throw new ServiceException("Could not process the FeatureCollection.");
		}
		
		return result;
	}

	private FeatureCollectionDocument createFeatureCollectionResponse(
			List<XmlObject> processCollection, FeatureCollectionType originalCollection) {
		FeatureCollectionDocument doc = FeatureCollectionDocument.Factory.newInstance();
		
		/*
		 * take the original response document
		 * and replace every member with the processed member
		 */
		for (int i = 0; i < originalCollection.getMemberArray().length; i++) {
			MemberPropertyType originalMember = originalCollection.getMemberArray(i);
			
			XmlCursor cur = originalMember.newCursor();
			cur.toFirstChild();
			
			XmlObject newMember = processCollection.get(i);
			cur.getObject().set(newMember);
		}
		
		doc.setFeatureCollection(originalCollection);
		return doc;
	}

	private List<XmlObject> processFeatureCollection(FeatureCollectionType fc) {
		List<XmlObject> processedCollection = new ArrayList<XmlObject>(fc.getMemberArray().length);
		
		for (MemberPropertyType member : fc.getMemberArray()) {
			XmlCursor cur = member.newCursor();
			cur.toFirstChild();
			XmlObject obj = cur.getObject();
			if (obj != null) {
				processedCollection.add(processFeature(obj));
			}
		}
		
		return processedCollection;
	}

	/**
	 * @param obj the original feature
	 * @return the WPS processed feature or the original if processing is not supported
	 */
	private XmlObject processFeature(XmlObject obj) {
		if (obj instanceof RouteSegmentType) {
			return invokeWPS((RouteSegmentType) obj);
		}
		return obj;
	}

	/**
	 * do the processing via WPS
	 * 
	 * @param obj the {@link RouteSegmentType} feature
	 * @return the processed feature
	 */
	private XmlObject invokeWPS(RouteSegmentType obj) {
		return wps.processRouteSegment(obj);
	}

	@SuppressWarnings("unchecked")
	private HttpResponse executeGetFeature(Map<?, ?> parameterMap) throws IOException {
		return executeHttpGet((Map<String, String[]>) parameterMap);
	}

	@Override
	public boolean supportsHttpGet() {
		return true;
	}

	@Override
	public boolean supportsHttpPost() {
		return true;
	}

}
