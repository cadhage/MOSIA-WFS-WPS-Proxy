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

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.n52.oxf.conversion.unit.CustomUnitConverter;
import org.n52.oxf.conversion.unit.NumberWithUOM;
import org.n52.oxf.conversion.unit.UOMTools;

import com.google.inject.Singleton;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

@Singleton
public class RouteUtil {
	
	public RouteUtil() {
		UOMTools.addCustomUnitConverter(new CustomUnitConverter() {
			
			private static final String RESULT_UOM = "[nmi_i]";
			
			@Override
			public NumberWithUOM convert(double doubleValue) {
				return new NumberWithUOM(doubleValue, RESULT_UOM);
			}

			@Override
			public String getBaseUnit() {
				return RESULT_UOM;
			}

			@Override
			public String getUnitString() {
				return "NM";
			}
		});
	}
	
	public Polygon routeToPolygon(Point start, Point end, NumberWithUOM widthLeft, NumberWithUOM widthRight,
			String srsName) {
		Ellipsoid ell = resolveEllipsoid(srsName);
		
		GeodeticCalculator calc = new GeodeticCalculator();
		
		GlobalCoordinates startCoord = createCoordinate(start);
		GlobalCoordinates endCoord = createCoordinate(end);
		
		GeodeticCurve curve = calc.calculateGeodeticCurve(ell, startCoord, endCoord);
		
		double routeBearing = curve.getAzimuth();
		
		double distanceLeft = convertToMeters(widthLeft);
		double distanceRight = convertToMeters(widthRight);
		
		return calculatePolygon(ell, startCoord, endCoord, routeBearing, distanceRight, distanceLeft);
	}

	private Ellipsoid resolveEllipsoid(String srsName) {
		/*
		 * TODO implement actual resolution
		 */
		return Ellipsoid.WGS84;
	}

	private Polygon calculatePolygon(Ellipsoid ell, GlobalCoordinates startCoord, GlobalCoordinates endCoord,
			double routeBearing, double distanceRight, double distanceLeft) {
		GeodeticCalculator calc = new GeodeticCalculator();
		
		GlobalCoordinates backRight = calc.calculateEndingGlobalCoordinates(ell, startCoord, routeBearing+90, distanceRight);
		GlobalCoordinates backLeft = calc.calculateEndingGlobalCoordinates(ell, startCoord, routeBearing-90, distanceLeft);
		
		GlobalCoordinates frontRight = calc.calculateEndingGlobalCoordinates(ell, endCoord, routeBearing+90, distanceRight);
		GlobalCoordinates frontLeft = calc.calculateEndingGlobalCoordinates(ell, endCoord, routeBearing-90, distanceLeft);
		
		GeometryFactory factory = new GeometryFactory();
		
		CoordinateSequence points = new CoordinateArraySequence(createCoordinateArray(backRight, backLeft, frontLeft, frontRight));
		
		LinearRing lr = new LinearRing(points, factory);
		
		return new Polygon(lr, null, factory);
	}

	private Coordinate[] createCoordinateArray(GlobalCoordinates... p) {
		Coordinate[] result = new Coordinate[p.length+1];
		
		int i = 0;
		for (GlobalCoordinates gc : p) {
			result[i++] = new Coordinate(gc.getLongitude(), gc.getLatitude());
		}
		result[p.length] = result[0];
		
		return result;
	}

	private double convertToMeters(NumberWithUOM number) {
		return UOMTools.convertToTargetUnit(number.getValue(), number.getUom(), "m");
	}

	private GlobalCoordinates createCoordinate(Point p) {
		return new GlobalCoordinates(p.getY(), p.getX());
	}

}
