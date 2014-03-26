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
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.ifgi.mosia.wpswfs.handler.RequestHandler;

@Singleton
public class BaseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4589872023160154399L;
	private static final Logger logger = LoggerFactory.getLogger(BaseServlet.class);
	
	@Inject
	Set<RequestHandler> handlers;

	@Override
	protected void doPost(final HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		final String content;
		final ContentType type;
		final String remoteHost;
		try {
			content = Util.readContent(req);
			type = ContentType.parse(req.getContentType());
			remoteHost = req.getRemoteHost();
		} catch (IOException e) {
			logger.warn(e.getMessage());
			return;
		}

		resp.setStatus(HttpStatus.SC_NO_CONTENT);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RequestHandler handler;
		try {
			assertServiceAndVersion(req);
			handler = resolveHandler(req);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
		
		handler.handleRequest(req, resp);
	}
	
	private RequestHandler resolveHandler(HttpServletRequest req) throws ServiceException {
		String request = req.getParameter("request");
		
		if (request == null || request.isEmpty()) {
			throw new ServiceException("The parameter 'request' was not provided");
		}
		
		
		for (RequestHandler h : handlers) {
			if (h.supportsRequestType(request)) {
				return h;
			}
		}
		
		throw new ServiceException("No handler for request type '"+ request+"' found");
	}

	private void assertServiceAndVersion(HttpServletRequest req) throws ServiceException {
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
