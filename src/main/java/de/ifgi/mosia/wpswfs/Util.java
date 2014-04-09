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
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.CharEncoding;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

public class Util {

	public static Set<String> readConfigFilePerLine(String resourcePath) throws IOException {
		URL resURL = Util.class.getResource(resourcePath);
		URLConnection resConn = resURL.openConnection();
		resConn.setUseCaches(false);
		InputStream contents = resConn.getInputStream();

		Scanner sc = new Scanner(contents);
		Set<String> result = new HashSet<String>();
		String line;
		while (sc.hasNext()) {
			line = sc.nextLine();
			if (line != null && !line.isEmpty() && !line.startsWith("#")) {
				result.add(line.trim());
			}
		}
		sc.close();

		return result;
	}
	
	public static String readContent(HttpServletRequest req)
			throws IOException {
		String enc = req.getCharacterEncoding();
		return readContent(req.getInputStream(), enc);
	}
	
	public static String readContent(InputStream is, String enc) {
		if (is == null) {
			return null;
		}
		
		Scanner sc = new Scanner(is, enc == null ? CharEncoding.ISO_8859_1 : enc);
		StringBuilder sb = new StringBuilder();

		while (sc.hasNext()) {
			sb.append(sc.nextLine());
		}

		sc.close();
		return sb.toString();
	}
	
	public static String readContent(InputStream is) {
		return readContent(is, null);
	}

	public static String readContent(HttpResponse response) throws IOException {
		Header encHeader = response.getEntity().getContentEncoding();
		String enc;
		if (encHeader != null) {
			enc = encHeader.getValue();
		}
		else {
			enc = CharEncoding.ISO_8859_1;
		}
		return readContent(response.getEntity().getContent(), enc);
	}
	
}
