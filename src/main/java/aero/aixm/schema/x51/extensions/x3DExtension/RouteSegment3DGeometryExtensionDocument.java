/*
 * An XML document type.
 * Localname: RouteSegment3DGeometryExtension
 * Namespace: http://www.aixm.aero/schema/5.1/extensions/3DExtension
 * Java type: aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument
 *
 * Automatically generated - do not modify.
 */
package aero.aixm.schema.x51.extensions.x3DExtension;


/**
 * A document containing one RouteSegment3DGeometryExtension(@http://www.aixm.aero/schema/5.1/extensions/3DExtension) element.
 *
 * This is a complex type.
 */
public interface RouteSegment3DGeometryExtensionDocument extends aero.aixm.schema.x51.AbstractRouteSegmentExtensionDocument
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(RouteSegment3DGeometryExtensionDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s2A8B1FACFC09E082FA947B7532DA0B4B").resolveHandle("routesegment3dgeometryextension3d99doctype");
    
    /**
     * Gets the "RouteSegment3DGeometryExtension" element
     */
    aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType getRouteSegment3DGeometryExtension();
    
    /**
     * Sets the "RouteSegment3DGeometryExtension" element
     */
    void setRouteSegment3DGeometryExtension(aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType routeSegment3DGeometryExtension);
    
    /**
     * Appends and returns a new empty "RouteSegment3DGeometryExtension" element
     */
    aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionType addNewRouteSegment3DGeometryExtension();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument newInstance() {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aero.aixm.schema.x51.extensions.x3DExtension.RouteSegment3DGeometryExtensionDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
