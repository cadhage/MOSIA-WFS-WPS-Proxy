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
package de.ifgi.mosia.geodesic;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.ifgi.mosia.wpswfs.wps.WPSConnector;
import aero.aixm.schema.x51.RouteSegmentDocument;
import aero.aixm.schema.x51.RouteSegmentType;

public class RouteSegmentExtensionTest {

	
	@Test
	public void encodeExtension() throws XmlException, IOException {
		Injector injector = Guice.createInjector();
		
		WPSConnector wps = injector.getInstance(WPSConnector.class);
		RouteSegmentType routeSegment = readXML();
		
		wps.processRouteSegment(routeSegment);
	}

	private RouteSegmentType readXML() throws XmlException, IOException {
		RouteSegmentDocument doc = RouteSegmentDocument.Factory.parse(getClass().getResource("/routeSegment.xml"));
		return doc.getRouteSegment();
	}
	
}
