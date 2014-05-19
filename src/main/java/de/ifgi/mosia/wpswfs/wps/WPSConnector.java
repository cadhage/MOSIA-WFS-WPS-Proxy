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
package de.ifgi.mosia.wpswfs.wps;

import java.util.Arrays;
import java.util.List;

import net.opengis.gml.x32.AbstractCurveSegmentType;
import net.opengis.gml.x32.CurveSegmentArrayPropertyType;
import net.opengis.gml.x32.LineStringSegmentType;
import net.opengis.gml.x32.ShellPropertyType;
import net.opengis.gml.x32.ShellType;
import net.opengis.gml.x32.SolidPropertyType;
import net.opengis.gml.x32.SolidType;

import org.apache.xmlbeans.XmlObject;
import org.n52.oxf.conversion.gml32.geometry.GeometryWithInterpolation;
import org.n52.oxf.conversion.gml32.xmlbeans.jts.GMLGeometryFactory;
import org.n52.oxf.conversion.unit.NumberWithUOM;

import aero.aixm.schema.x51.CurvePropertyType;
import aero.aixm.schema.x51.CurveType;
import aero.aixm.schema.x51.RouteSegmentTimeSlicePropertyType;
import aero.aixm.schema.x51.RouteSegmentType;
import aero.aixm.schema.x51.ValDistanceType;
import aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vividsolutions.jts.geom.Coordinate;

import de.ifgi.mosia.geodesic.RouteUtil;

@Singleton
public class WPSConnector {
	
	@Inject
	RouteUtil routeUtil;

	public XmlObject processRouteSegment(RouteSegmentType routeSegmentType) {
		RouteSegmentTimeSlicePropertyType ts = routeSegmentType.getTimeSliceArray(0);
		
		if (ts != null && ts.getRouteSegmentTimeSlice().isSetCurveExtent()) {
			NumberWithUOM widthLeft = createNumberWithUOM(ts.getRouteSegmentTimeSlice().getWidthLeft());
			NumberWithUOM widthRight = createNumberWithUOM(ts.getRouteSegmentTimeSlice().getWidthRight());
			
			CurvePropertyType curveExt = ts.getRouteSegmentTimeSlice().getCurveExtent();
			CurveType curve = curveExt.getCurve();
			
			CurveSegmentArrayPropertyType segments = curve.getSegments();
			
			List<Coordinate> coords = null;
			for (AbstractCurveSegmentType seg : segments.getAbstractCurveSegmentArray()) {
				if (seg instanceof LineStringSegmentType) {
					LineStringSegmentType lineString = (LineStringSegmentType) seg;
					GeometryWithInterpolation geom = GMLGeometryFactory.createLineString(lineString, null);
					
					coords = Arrays.asList(geom.getGeometry().getCoordinates());
				}
			}
			
			if (coords != null && coords.size() > 1) {
				ShellType shell = createShell(coords, widthLeft, widthRight);
				ts.getRouteSegmentTimeSlice().addNewExtension().set(createExtension(shell));
			}
		}
		
		return routeSegmentType;
	}

	private ShellType createShell(List<Coordinate> coords,
			NumberWithUOM widthLeft, NumberWithUOM widthRight) {
		Object poly = routeUtil.routeToPolygon(coords.get(0), coords.get(coords.size() - 1),
				widthLeft, widthRight, null);
		
		
		return null;
	}

	private XmlObject createExtension(ShellType shell) {
		RouteSegment3DGeometryExtensionType ext = RouteSegment3DGeometryExtensionType.Factory.newInstance();
		
		SolidPropertyType curve3D = ext.addNewCurveExtend3D();
		
		SolidType solid = SolidType.Factory.newInstance();
		
		ShellPropertyType shellProp = ShellPropertyType.Factory.newInstance();
		shellProp.setShell(shell);
		solid.setExterior(shellProp);
		
		curve3D.setAbstractSolid(solid);
		
		return ext;
	}

	private NumberWithUOM createNumberWithUOM(ValDistanceType vald) {
		if (vald == null) {
			/*
			 * TODO exception handling
			 */
			return new NumberWithUOM(5.0, "NM");
		}
		
		String uom;
		if (vald.isSetUom()) {
			uom = vald.getUom();
		}
		else {
			uom = "NM";
		}
		
		return new NumberWithUOM(vald.getBigDecimalValue().doubleValue(), uom);
	}

}
