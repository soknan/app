/**
 * IUtil.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface IUtil extends java.rmi.Remote {
    public java.lang.String encryptOrDecrypt(java.lang.String tokenString, java.lang.String txt, java.lang.String salt, java.lang.Boolean isDec) throws java.rmi.RemoteException;
}
