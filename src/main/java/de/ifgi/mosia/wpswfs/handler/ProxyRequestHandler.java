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

import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.inject.Inject;

import de.ifgi.mosia.wpswfs.Configuration;
import de.ifgi.mosia.wpswfs.Util;

public abstract class ProxyRequestHandler {

	@Inject
	Configuration config;
	
	protected HttpResponse executeHttpGet(Map<String, String[]> urlParameters) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		for (String key : urlParameters.keySet()) {
			String[] val = urlParameters.get(key);
			for (String v : val) {
				sb.append(key);
				sb.append("=");
				sb.append(v);
				sb.append("&");
			}
		}
		
		sb.deleteCharAt(sb.length()-1);
		
		return executeHttpGet(sb.toString());
	}
	
	protected HttpResponse executeHttpGet(String subUrl) throws IOException {
		HttpGet get = new HttpGet(
				String.format("%s?%s", config.getWFSURL(), subUrl));
		
		DefaultHttpClient client = new DefaultHttpClient();
		
		return client.execute(get);
	}
	
	protected void filterAndWriteResponse(HttpEntity entity,
			HttpServletResponse resp) throws IOException {
		String enc = entity.getContentEncoding() == null ? null : entity.getContentEncoding().getValue();
		String content = Util.readContent(entity.getContent(), enc);
		
		String filteredContent = content.replace(config.getWFSURL(), config.getServiceURL());
		
		writeResponse(filteredContent, enc, entity.getContentType(), resp);
	}

	protected void writeResponse(HttpEntity entity,
			HttpServletResponse resp) throws IOException {
		String enc = entity.getContentEncoding() == null ? null : entity.getContentEncoding().getValue();
		
		if (enc != null && enc.isEmpty()) {
			enc = null;
		}
		
		writeResponse(Util.readContent(entity.getContent(), enc), enc, entity.getContentType(), resp);
	}
	
	protected void writeResponse(String filteredContent, String enc,
			Header contentType, HttpServletResponse resp) throws IOException {
		if (enc == null) {
			enc = "UTF-8";
		}
		
		byte[] bytes = filteredContent.getBytes(enc);
		
		resp.setContentType(contentType == null ? "application/xml" : contentType.getValue());
		resp.setCharacterEncoding(enc);
		resp.setContentLength(bytes.length);
		
		resp.setStatus(HttpStatus.SC_OK);
		
		resp.getOutputStream().write(bytes);
		resp.getOutputStream().flush();		
	}
}
