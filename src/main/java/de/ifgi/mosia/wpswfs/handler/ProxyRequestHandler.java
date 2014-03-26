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

import javax.servlet.http.HttpServletResponse;

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
	
	protected HttpResponse executeHttpGet(String subUrl) throws IOException {
		HttpGet get = new HttpGet(
				String.format("%s?service=WFS&acceptversions=2.0.0&%s", config.getWFSURL(), subUrl));
		
		DefaultHttpClient client = new DefaultHttpClient();
		
		return client.execute(get);
	}
	
	protected void filterAndWriteResponse(HttpEntity entity,
			HttpServletResponse resp) throws IllegalStateException, IOException {
		String enc = entity.getContentEncoding() == null ? null : entity.getContentEncoding().getValue();
		String content = Util.readContent(entity.getContent(), enc);
		
		String filteredContent = content.replace(config.getWFSURL(), config.getServiceURL());
		
		if (enc == null) {
			enc = "UTF-8";
		}
		
		byte[] bytes = filteredContent.getBytes(enc);
		
		resp.setContentType(entity.getContentType() == null ? "application/xml" : entity.getContentType().getValue());
		resp.setCharacterEncoding(enc);
		resp.setContentLength(bytes.length);
		
		resp.setStatus(HttpStatus.SC_OK);
		
		resp.getOutputStream().write(bytes);
		resp.getOutputStream().flush();
		
	}
}
