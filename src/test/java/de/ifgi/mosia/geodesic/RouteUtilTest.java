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

import org.junit.Assert;
import org.junit.Test;
import org.n52.oxf.conversion.unit.NumberWithUOM;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

public class RouteUtilTest {

	private static final double TOLERANCE = 0.00000005;

	@Test
	public void testTransformationToPolygon() {
		GeometryFactory factory = new GeometryFactory();
		Point start = createPoint(factory, 41.5301944, -5.6396944);
		Point end = createPoint(factory, 41.7865556, -6.5608611);
		
		Polygon poly = new RouteUtil().routeToPolygon(start, end, new NumberWithUOM(5, "[nmi_i]"), new NumberWithUOM(6, "[nmi_i]"), "urn:ogc:def:crs:OGC:1.3:CRS84");
		
		Coordinate[] coords = poly.getCoordinates();
		
		Assert.assertTrue(Math.abs(coords[0].x - (-5.678804997299011)) <= TOLERANCE);
		Assert.assertTrue(Math.abs(coords[0].y - (41.45217671744223)) <= TOLERANCE);
		
		Assert.assertTrue(Math.abs(coords[1].x - (-6.600126682886497)) <= TOLERANCE);
		Assert.assertTrue(Math.abs(coords[1].y - (41.7085413485116)) <= TOLERANCE);
		
		Assert.assertTrue(Math.abs(coords[2].x - (-6.51361654949153)) <= TOLERANCE);
		Assert.assertTrue(Math.abs(coords[2].y - (41.88015353501876)) <= TOLERANCE);

		Assert.assertTrue(Math.abs(coords[3].x - (-5.592637452946747)) <= TOLERANCE);
		Assert.assertTrue(Math.abs(coords[3].y - (41.62379661198949)) <= TOLERANCE);
	}

	private Point createPoint(GeometryFactory factory, double lat, double lon) {
		return new Point(new CoordinateArraySequence(new Coordinate[] {new Coordinate(lon, lat)}), factory);
	}
	
}
