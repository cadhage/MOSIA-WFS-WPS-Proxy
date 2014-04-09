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

import org.apache.commons.codec.CharEncoding;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

import de.ifgi.mosia.wpswfs.ServiceException;
import de.ifgi.mosia.wpswfs.Util;

@Singleton
public class GenericRequestHandler extends ProxyRequestHandler implements RequestHandler {

	private static final Logger logger = LoggerFactory.getLogger(GenericRequestHandler.class);
	
	@Override
	public boolean supportsRequestType(String request) {
		return true;
	}

	@Override
	public boolean supportsHttpGet() {
		return true;
	}

	@Override
	public boolean supportsHttpPost() {
		return true;
	}

	@Override
	public void handleRequest(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServiceException {
		HttpResponse response;
		if (req.getMethod().equalsIgnoreCase("GET")) {
			response = handleGet(req, resp);
		}
		else if (req.getMethod().equalsIgnoreCase("POST")) {
			response = handlePost(req, resp);
		}
		else {
			throw new UnsupportedOperationException("Only GET and POST are supported.");
		}
		
		writeResponse(response, resp);
	}

	protected HttpResponse handlePost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
		String enc = req.getCharacterEncoding();
		
		if (enc == null || enc.isEmpty()) {
			enc = CharEncoding.ISO_8859_1;
		}
		
		String content = Util.readContent(req.getInputStream(), enc);
		
		HttpResponse response;
		
		try {
			response = executeHttpPost(content, enc);
		}
		catch (IOException e) {
			logger.warn(e.getMessage(), e);
			throw new IOException("Proxy server issue: "+e.getMessage());
		}
		
		return response;
	}


	@SuppressWarnings("unchecked")
	protected HttpResponse handleGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
		HttpResponse response;
		try {
			response = executeHttpGet((Map<String, String[]>) req.getParameterMap());
		}
		catch (IOException e) {
			logger.warn(e.getMessage(), e);
			throw new IOException("Proxy server issue: "+e.getMessage());
		}
		
		return response;
	}

}
