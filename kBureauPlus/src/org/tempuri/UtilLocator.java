/**
 * UtilLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class UtilLocator extends org.apache.axis.client.Service implements org.tempuri.Util {

    public UtilLocator() {
    }


    public UtilLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public UtilLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IUtil
    private java.lang.String BasicHttpBinding_IUtil_address = "http://192.168.100.2:4012/services/Util.svc";

    public java.lang.String getBasicHttpBinding_IUtilAddress() {
        return BasicHttpBinding_IUtil_address;
    }

    // The WSDD service label defaults to the port label.
    private java.lang.String BasicHttpBinding_IUtilWSDDServiceName = "BasicHttpBinding_IUtil";

    public java.lang.String getBasicHttpBinding_IUtilWSDDServiceName() {
        return BasicHttpBinding_IUtilWSDDServiceName;
    }

    public void setBasicHttpBinding_IUtilWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_IUtilWSDDServiceName = name;
    }

    public org.tempuri.IUtil getBasicHttpBinding_IUtil() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IUtil_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IUtil(endpoint);
    }

    public org.tempuri.IUtil getBasicHttpBinding_IUtil(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.BasicHttpBinding_IUtilStub _stub = new org.tempuri.BasicHttpBinding_IUtilStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IUtilWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IUtilEndpointAddress(java.lang.String address) {
        BasicHttpBinding_IUtil_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.IUtil.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.BasicHttpBinding_IUtilStub _stub = new org.tempuri.BasicHttpBinding_IUtilStub(new java.net.URL(BasicHttpBinding_IUtil_address), this);
                _stub.setPortName(getBasicHttpBinding_IUtilWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BasicHttpBinding_IUtil".equals(inputPortName)) {
            return getBasicHttpBinding_IUtil();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "Util");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IUtil"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port label.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IUtil".equals(portName)) {
            setBasicHttpBinding_IUtilEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port label.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
