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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.opengis.gml.x32.AbstractCurveSegmentType;
import net.opengis.gml.x32.AbstractRingPropertyType;
import net.opengis.gml.x32.CurveSegmentArrayPropertyType;
import net.opengis.gml.x32.DirectPositionListType;
import net.opengis.gml.x32.LineStringSegmentType;
import net.opengis.gml.x32.LinearRingDocument;
import net.opengis.gml.x32.LinearRingType;
import net.opengis.gml.x32.PolygonDocument;
import net.opengis.gml.x32.PolygonType;
import net.opengis.gml.x32.PosListDocument;
import net.opengis.gml.x32.ShellPropertyType;
import net.opengis.gml.x32.ShellType;
import net.opengis.gml.x32.SolidDocument;
import net.opengis.gml.x32.SolidPropertyType;
import net.opengis.gml.x32.SolidType;
import net.opengis.gml.x32.SurfacePropertyType;

import org.apache.xmlbeans.XmlObject;
import org.n52.oxf.conversion.gml32.geometry.GeometryWithInterpolation;
import org.n52.oxf.conversion.gml32.xmlbeans.jts.GMLGeometryFactory;
import org.n52.oxf.conversion.unit.NumberWithUOM;
import org.n52.oxf.xmlbeans.tools.XmlUtil;

import aero.aixm.schema.x51.CurvePropertyType;
import aero.aixm.schema.x51.CurveType;
import aero.aixm.schema.x51.RouteSegmentTimeSlicePropertyType;
import aero.aixm.schema.x51.RouteSegmentType;
import aero.aixm.schema.x51.ValDistanceType;
import aero.aixm.schema.x51.ValDistanceVerticalType;
import aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument;
import aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Polygon;

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
			
			NumberWithUOM lowerLimit = createNumberWithUOM(ts.getRouteSegmentTimeSlice().getLowerLimit());
			NumberWithUOM upperLimit = createNumberWithUOM(ts.getRouteSegmentTimeSlice().getUpperLimit());
			
			
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
				ShellType shell = createShell(coords, widthLeft, widthRight, lowerLimit, upperLimit);
				ts.getRouteSegmentTimeSlice().addNewExtension().set(createExtension(shell));
			}
		}
		
		return routeSegmentType;
	}

	private ShellType createShell(List<Coordinate> coords,
			NumberWithUOM widthLeft, NumberWithUOM widthRight,
			NumberWithUOM lowerLimit, NumberWithUOM upperLimit) {
		/*
		 * make a polygon with left and right legs from first to last coord
		 */
		Polygon poly = routeUtil.routeToPolygon(coords.get(0), coords.get(coords.size() - 1),
				widthLeft, widthRight, null);
		
		Coordinate[] coordArray = poly.getCoordinates();
		/*
		 * bottom face is A, B, C, D, A
		 */
		Coordinate a = new Coordinate(coordArray[0]);
		a.z = lowerLimit.getValue();
		Coordinate b = new Coordinate(coordArray[1]);
		b.z = a.z;
		Coordinate c = new Coordinate(coordArray[2]);
		c.z = a.z;
		Coordinate d = new Coordinate(coordArray[3]);
		d.z = a.z;
		
		/*
		 * top face is W, Z, Y, X W
		 */
		Coordinate w = new Coordinate(coordArray[0]);
		w.z = upperLimit.getValue();
		Coordinate x = new Coordinate(coordArray[1]);
		x.z = w.z;
		Coordinate y = new Coordinate(coordArray[2]);
		y.z = w.z;
		Coordinate z = new Coordinate(coordArray[3]);
		z.z = w.z;
		
		ShellType shell = ShellType.Factory.newInstance();
		createSolidFace(shell.addNewSurfaceMember(), new Coordinate[] {a, b, c, d, a});
		createSolidFace(shell.addNewSurfaceMember(), new Coordinate[] {w, z, y, x, w});
		createSolidFace(shell.addNewSurfaceMember(), new Coordinate[] {a, w, z, d, a});
		createSolidFace(shell.addNewSurfaceMember(), new Coordinate[] {b, c, y, x, b});
		createSolidFace(shell.addNewSurfaceMember(), new Coordinate[] {a, b, x, w, a});
		createSolidFace(shell.addNewSurfaceMember(), new Coordinate[] {d, z, y, c, d});
		
		return shell;
	}

	private void createSolidFace(SurfacePropertyType target,
			Coordinate[] coordinates) {
		PolygonType poly = PolygonType.Factory.newInstance();
		
		AbstractRingPropertyType ext = poly.addNewExterior();
		
		LinearRingType lr = LinearRingType.Factory.newInstance();
		
		PosListDocument posListDoc = PosListDocument.Factory.newInstance();
		DirectPositionListType posList = posListDoc.addNewPosList();
		
		List<Double> coordList = new ArrayList<Double>(coordinates.length * 3);
		
		for (Coordinate c : coordinates) {
			coordList.add(c.x);
			coordList.add(c.y);
			coordList.add(c.z);
		}
		
		posList.setListValue(coordList);
		
		lr.setPosList(posList);
		
		ext.setAbstractRing(lr);
		XmlUtil.qualifySubstitutionGroup(ext.getAbstractRing(), LinearRingDocument.type.getDocumentElementName());
		
		target.setAbstractSurface(poly);
		XmlUtil.qualifySubstitutionGroup(target.getAbstractSurface(), PolygonDocument.type.getDocumentElementName());
	}

	private XmlObject createExtension(ShellType shell) {
		RouteSegment3DGeometryExtensionDocument doc = RouteSegment3DGeometryExtensionDocument.Factory.newInstance();
		RouteSegment3DGeometryExtensionType ext = doc.addNewRouteSegment3DGeometryExtension();
		
		SolidPropertyType curve3D = ext.addNewCurveExtend3D();
		
		SolidType solid = SolidType.Factory.newInstance();
		
		ShellPropertyType shellProp = ShellPropertyType.Factory.newInstance();
		shellProp.setShell(shell);
		solid.setExterior(shellProp);
		
		curve3D.setAbstractSolid(solid);
		XmlUtil.qualifySubstitutionGroup(curve3D.getAbstractSolid(), SolidDocument.type.getDocumentElementName());
		
		return doc;
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
	
	private NumberWithUOM createNumberWithUOM(ValDistanceVerticalType vald) {
		if (vald == null) {
			/*
			 * TODO exception handling
			 */
			return new NumberWithUOM(0, "FL");
		}
		
		String uom;
		if (vald.isSetUom()) {
			uom = vald.getUom();
		}
		else {
			uom = "FL";
		}
		
		return new NumberWithUOM(Double.parseDouble(vald.getStringValue()), uom);
	}

}
