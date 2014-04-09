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

import org.apache.xmlbeans.XmlOptions;

import net.opengis.ows.x11.ExceptionReportDocument;
import net.opengis.ows.x11.ExceptionReportDocument.ExceptionReport;
import net.opengis.ows.x11.ExceptionType;

public class ExceptionReportWrapper {

	private Exception exception;

	public ExceptionReportWrapper(Exception exception) {
		this.exception = exception;
	}

	public String toXML() {
		ExceptionReportDocument doc = ExceptionReportDocument.Factory.newInstance();
		ExceptionReport report = doc.addNewExceptionReport();

		ExceptionType exc = report.addNewException();
		exc.setExceptionCode("RECEIVER");
		exc.addExceptionText(encodeException());
		
		report.setLang("eng");
		
		return doc.xmlText(new XmlOptions().setSavePrettyPrint());
	}

	private String encodeException() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.exception.getClass().getName());
		sb.append(":\n");
		
		for (StackTraceElement ste : this.exception.getStackTrace()) {
			sb.append(ste.toString());
			sb.append("\n");
		}
		
		return sb.toString();
	}

}
