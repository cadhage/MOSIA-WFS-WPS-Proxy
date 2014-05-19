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

import net.opengis.gml.x32.SolidPropertyType;
import net.opengis.gml.x32.SolidType;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.ifgi.mosia.wpswfs.wps.WPSConnector;
import aero.aixm.schema.x51.AbstractExtensionDocument;
import aero.aixm.schema.x51.RouteSegmentDocument;
import aero.aixm.schema.x51.RouteSegmentTimeSliceType;
import aero.aixm.schema.x51.RouteSegmentTimeSliceType.Extension;
import aero.aixm.schema.x51.RouteSegmentType;
import aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument;

public class RouteSegmentExtensionTest {

	
	@Test
	public void encodeExtension() throws XmlException, IOException {
		Injector injector = Guice.createInjector();
		
		WPSConnector wps = injector.getInstance(WPSConnector.class);
		RouteSegmentType routeSegment = readXML();
		
		XmlObject seg = wps.processRouteSegment(routeSegment);
		
		if (seg instanceof RouteSegmentType) {
			RouteSegmentType rs = (RouteSegmentType) seg;
			
			RouteSegmentTimeSliceType ts = rs.getTimeSliceArray(0).getRouteSegmentTimeSlice();
			
			Assert.assertTrue("No extension present", ts.getExtensionArray() != null && ts.getExtensionArray().length > 0);
			
			Extension ext = ts.getExtensionArray(0);
			
			AbstractExtensionDocument mosiaExt = RouteSegment3DGeometryExtensionDocument.Factory.parse(ext.xmlText());
			
			if (mosiaExt instanceof RouteSegment3DGeometryExtensionDocument) {
				RouteSegment3DGeometryExtensionDocument route3d = (RouteSegment3DGeometryExtensionDocument) mosiaExt;
				
				SolidPropertyType solidProp = route3d.getRouteSegment3DGeometryExtension().getCurveExtend3D();
				
				SolidType solid = (SolidType) solidProp.getAbstractSolid();
				
				Assert.assertTrue("Unexpected surface count", solid.getExterior().getShell().getSurfaceMemberArray().length == 6);
			}
			else {
				Assert.fail("not the expected extension!");
			}
		}
		else {
			Assert.fail("not a RouteSegmentType!");
		}
	}

	private RouteSegmentType readXML() throws XmlException, IOException {
		RouteSegmentDocument doc = RouteSegmentDocument.Factory.parse(getClass().getResource("/routeSegment.xml"));
		return doc.getRouteSegment();
	}
	
}
