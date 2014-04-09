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
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

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
	
	protected HttpResponse executeHttpPost(String content, String enc) throws IOException {
		HttpPost post = new HttpPost(config.getWFSURL());
		
		post.setEntity(new StringEntity(content, enc));
		
		return executeHttpPost(post);
	}
	
	protected HttpResponse executeHttpPost(HttpPost post) throws IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		return client.execute(post);
	}
	
	protected void filterAndWriteResponse(HttpEntity entity,
			int statusCode, HttpServletResponse resp) throws IOException {
		String enc = entity.getContentEncoding() == null ? null : entity.getContentEncoding().getValue();
		String content = Util.readContent(entity.getContent(), enc);
		
		String filteredContent = content.replace(config.getWFSURL(), config.getServiceURL());
		
		writeResponse(filteredContent, enc, entity.getContentType(), statusCode, resp);
	}

	protected void writeResponse(HttpResponse proxyResponse,
			HttpServletResponse resp) throws IOException {
		HttpEntity entity = proxyResponse.getEntity();
		
		String enc;
		InputStream content;
		Header contentType;
		if (entity == null) {
			enc = null;	
			content = null;
			contentType = null;
		}
		else {
			enc = entity.getContentEncoding() == null ? null : entity.getContentEncoding().getValue();
			content = entity.getContent();
			contentType = entity.getContentType();
		}
		
		
		
		if (enc != null && enc.isEmpty()) {
			enc = null;
		}
		
		writeResponse(Util.readContent(content, enc), enc, contentType,
				proxyResponse.getStatusLine().getStatusCode(), resp);
	}
	
	protected void writeResponse(String filteredContent, String enc,
			Header contentType, int statusCode, HttpServletResponse resp) throws IOException {
		if (enc == null) {
			enc = "UTF-8";
		}
		
		byte[] bytes;
		if (statusCode == HttpStatus.SC_NO_CONTENT) {
			bytes = new byte[0];
		}
		else if (filteredContent == null) {
			/*
			 * recursive call end exit
			 */
			writeResponse(new ExceptionReportWrapper(new IOException("Proxy server issue")).toXML(),
					"UTF-8", new BasicHeader("Content-Type", "application/xml"),
					HttpStatus.SC_INTERNAL_SERVER_ERROR, resp);
			return;
		}
		else {
			bytes = filteredContent.getBytes(enc);
		}
		
		resp.setContentType(contentType == null ? "application/xml" : contentType.getValue());
		resp.setCharacterEncoding(enc);
		resp.setContentLength(bytes.length);
		
		resp.setStatus(statusCode);
		
		resp.getOutputStream().write(bytes);
		resp.getOutputStream().flush();		
	}
}
