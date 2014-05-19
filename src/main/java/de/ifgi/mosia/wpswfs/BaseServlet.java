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
package de.ifgi.mosia.wpswfs;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.CharEncoding;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.ifgi.mosia.wpswfs.handler.GenericRequestHandler;
import de.ifgi.mosia.wpswfs.handler.RequestHandler;

@Singleton
public class BaseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4589872023160154399L;
	
	@Inject
	Set<RequestHandler> handlers;
	
	@Inject
	GenericRequestHandler genericHandler;

	@Override
	protected void doPost(final HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RequestHandler handler;
		String payload;
		String enc;
		try {
			enc = req.getCharacterEncoding();
			
			if (enc == null || enc.isEmpty()) {
				enc = CharEncoding.ISO_8859_1;
			}
			
			payload = Util.readContent(req.getInputStream(), enc);
			handler = resolveHandlerFromPost(payload);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
		
		if (handler == null) {
			handler = genericHandler;
		}
		
		try {
			handler.handlePostRequest(req, resp, payload, enc);
		} catch (ServiceException e) {
			throw new IOException(e);
		}
	}

	private RequestHandler resolveHandlerFromPost(String payload) throws ServiceException, IOException {
		if (payload == null || payload.isEmpty()) {
			throw new ServiceException("The parameter 'request' was not provided");
		}
		
		
		for (RequestHandler h : handlers) {
			if (h.supportsPostPayload(payload)) {
				return h;
			}
		}
		
		return null;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RequestHandler handler;
		try {
//			assertServiceAndVersion(req);
			handler = resolveHandler(req);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
		
		if (handler == null) {
			handler = genericHandler;
		}
		
		try {
			handler.handleRequest(req, resp);
		} catch (ServiceException e) {
			throw new IOException(e);
		}
	}
	
	private RequestHandler resolveHandler(HttpServletRequest req) throws ServiceException {
		String request = req.getParameter("request");
		
		if (request == null || request.isEmpty()) {
			Enumeration<?> it = req.getParameterNames();
			while (it.hasMoreElements()) {
				String next = it.nextElement().toString();
				if (next.equalsIgnoreCase("request")) {
					request = req.getParameter(next);
					break;
				}
			}	
		}
		
		if (request == null || request.isEmpty()) {
			throw new ServiceException("The parameter 'request' was not provided");
		}
		
		
		for (RequestHandler h : handlers) {
			if (h.supportsRequestType(request)) {
				return h;
			}
		}
		
		return null;
	}

	protected void assertServiceAndVersion(HttpServletRequest req) throws ServiceException {
		String result = req.getParameter("service");
		String version = req.getParameter("version");
		
		if (result == null || !result.equalsIgnoreCase("WFS")) {
			throw new ServiceException("'service' parameter must be set to 'WFS'");
		}
		
		if (version == null) {
			version = req.getParameter("acceptversions");
			
			if (version == null || !version.equalsIgnoreCase("2.0.0")) {
				throw new ServiceException("'version' (or 'acceptversions') parameter must be set to '2.0.0'");
			}
		}
		
	}


}
