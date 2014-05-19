/*
 * An XML document type.
 * Localname: RouteSegment3DGeometryExtension
 * Namespace: http://www.aixm.aero/schema/5.1/extensions/3DExtension
 * Java type: aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument
 *
 * Automatically generated - do not modify.
 */
package aero.aixm.schema.x51.extensions.x3DExtension.impl;
/**
 * A document containing one RouteSegment3DGeometryExtension(@http://www.aixm.aero/schema/5.1/extensions/3DExtension) element.
 *
 * This is a complex type.
 */
public class RouteSegment3DGeometryExtensionDocumentImpl extends aero.aixm.schema.x51.impl.AbstractRouteSegmentExtensionDocumentImpl implements aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument
{
    private static final long serialVersionUID = 1L;
    
    public RouteSegment3DGeometryExtensionDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ROUTESEGMENT3DGEOMETRYEXTENSION$0 = 
        new javax.xml.namespace.QName("http://www.aixm.aero/schema/5.1/extensions/3DExtension", "RouteSegment3DGeometryExtension");
    
    
    /**
     * Gets the "RouteSegment3DGeometryExtension" element
     */
    public aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType getRouteSegment3DGeometryExtension()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType target = null;
            target = (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType)get_store().find_element_user(ROUTESEGMENT3DGEOMETRYEXTENSION$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "RouteSegment3DGeometryExtension" element
     */
    public void setRouteSegment3DGeometryExtension(aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType routeSegment3DGeometryExtension)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType target = null;
            target = (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType)get_store().find_element_user(ROUTESEGMENT3DGEOMETRYEXTENSION$0, 0);
            if (target == null)
            {
                target = (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType)get_store().add_element_user(ROUTESEGMENT3DGEOMETRYEXTENSION$0);
            }
            target.set(routeSegment3DGeometryExtension);
        }
    }
    
    /**
     * Appends and returns a new empty "RouteSegment3DGeometryExtension" element
     */
    public aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType addNewRouteSegment3DGeometryExtension()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType target = null;
            target = (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType)get_store().add_element_user(ROUTESEGMENT3DGEOMETRYEXTENSION$0);
            return target;
        }
    }
}
