/*
 * XML Type:  RouteSegment3DGeometryExtensionType
 * Namespace: http://www.aixm.aero/schema/5.1/extensions/3DExtension
 * Java type: aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType
 *
 * Automatically generated - do not modify.
 */
package aero.aixm.schema.x51.extensions.x3DExtension.impl;
/**
 * An XML RouteSegment3DGeometryExtensionType(@http://www.aixm.aero/schema/5.1/extensions/3DExtension).
 *
 * This is a complex type.
 */
public class RouteSegment3DGeometryExtensionTypeImpl extends aero.aixm.schema.x51.impl.AbstractExtensionTypeImpl implements aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType
{
    private static final long serialVersionUID = 1L;
    
    public RouteSegment3DGeometryExtensionTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CURVEEXTEND3D$0 = 
        new javax.xml.namespace.QName("http://www.aixm.aero/schema/5.1/extensions/3DExtension", "curveExtend3D");
    
    
    /**
     * Gets the "curveExtend3D" element
     */
    public net.opengis.gml.x32.SolidPropertyType getCurveExtend3D()
    {
        synchronized (monitor())
        {
            check_orphaned();
            net.opengis.gml.x32.SolidPropertyType target = null;
            target = (net.opengis.gml.x32.SolidPropertyType)get_store().find_element_user(CURVEEXTEND3D$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "curveExtend3D" element
     */
    public void setCurveExtend3D(net.opengis.gml.x32.SolidPropertyType curveExtend3D)
    {
        synchronized (monitor())
        {
            check_orphaned();
            net.opengis.gml.x32.SolidPropertyType target = null;
            target = (net.opengis.gml.x32.SolidPropertyType)get_store().find_element_user(CURVEEXTEND3D$0, 0);
            if (target == null)
            {
                target = (net.opengis.gml.x32.SolidPropertyType)get_store().add_element_user(CURVEEXTEND3D$0);
            }
            target.set(curveExtend3D);
        }
    }
    
    /**
     * Appends and returns a new empty "curveExtend3D" element
     */
    public net.opengis.gml.x32.SolidPropertyType addNewCurveExtend3D()
    {
        synchronized (monitor())
        {
            check_orphaned();
            net.opengis.gml.x32.SolidPropertyType target = null;
            target = (net.opengis.gml.x32.SolidPropertyType)get_store().add_element_user(CURVEEXTEND3D$0);
            return target;
        }
    }
}
