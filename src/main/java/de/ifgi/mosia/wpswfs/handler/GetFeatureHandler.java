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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetFeatureHandler extends GenericRequestHandler {

	private static final Logger logger = LoggerFactory.getLogger(GetFeatureHandler.class);
	
	@Override
	public boolean supportsRequestType(String request) {
		return "GetFeature".equals(request);
	}

	@Override
	protected void handleGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
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
			postProcessFeaturesAndWriteResponse(response.getEntity(), resp);
		}
	}

	private void postProcessFeaturesAndWriteResponse(HttpEntity entity,
			HttpServletResponse resp) throws IOException {
		writeResponse(entity, resp);
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