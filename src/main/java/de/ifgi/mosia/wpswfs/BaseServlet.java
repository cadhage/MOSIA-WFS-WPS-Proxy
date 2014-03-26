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
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.CharEncoding;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

@Singleton
public class BaseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4589872023160154399L;
	private static final Logger logger = LoggerFactory.getLogger(BaseServlet.class);
	private ExecutorService executor = Executors.newCachedThreadPool();

	@Override
	protected void doPost(final HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		final String content;
		final ContentType type;
		final String remoteHost;
		try {
			content = readContent(req);
			type = ContentType.parse(req.getContentType());
			remoteHost = req.getRemoteHost();
		} catch (IOException e) {
			logger.warn(e.getMessage());
			return;
		}

		resp.setStatus(HttpStatus.SC_NO_CONTENT);
	}

	protected String readContent(HttpServletRequest req)
			throws IOException {
		String enc = req.getCharacterEncoding();
		Scanner sc = new Scanner(req.getInputStream(), enc == null ? CharEncoding.ISO_8859_1 : enc);
		StringBuilder sb = new StringBuilder();

		while (sc.hasNext()) {
			sb.append(sc.nextLine());
		}

		sc.close();
		return sb.toString();
	}

}
