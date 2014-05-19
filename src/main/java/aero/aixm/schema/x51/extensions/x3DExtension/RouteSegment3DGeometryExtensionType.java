/*
 * XML Type:  RouteSegment3DGeometryExtensionType
 * Namespace: http://www.aixm.aero/schema/5.1/extensions/3DExtension
 * Java type: aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType
 *
 * Automatically generated - do not modify.
 */
package aero.aixm.schema.x51.extensions.x3DExtension;


/**
 * An XML RouteSegment3DGeometryExtensionType(@http://www.aixm.aero/schema/5.1/extensions/3DExtension).
 *
 * This is a complex type.
 */
public interface RouteSegment3DGeometryExtensionType extends aero.aixm.schema.x51.AbstractExtensionType
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(RouteSegment3DGeometryExtensionType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s2A8B1FACFC09E082FA947B7532DA0B4B").resolveHandle("routesegment3dgeometryextensiontype176ftype");
    
    /**
     * Gets the "curveExtend3D" element
     */
    net.opengis.gml.x32.SolidPropertyType getCurveExtend3D();
    
    /**
     * Sets the "curveExtend3D" element
     */
    void setCurveExtend3D(net.opengis.gml.x32.SolidPropertyType curveExtend3D);
    
    /**
     * Appends and returns a new empty "curveExtend3D" element
     */
    net.opengis.gml.x32.SolidPropertyType addNewCurveExtend3D();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType newInstance() {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
