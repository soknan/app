/**
 * IKservice.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface IKservice extends java.rmi.Remote {
    public org.datacontract.schemas.kservice.model.datacontracts.Authentication login(java.lang.String tokenString, org.datacontract.schemas.kservice.model.datacontracts.Authentication authentication, java.lang.Boolean isTest) throws java.rmi.RemoteException;
    public java.lang.String getConnectionString(java.lang.String tokenString, java.lang.Boolean isTest) throws java.rmi.RemoteException;
    public java.lang.Boolean testConnection(java.lang.String tokenString, java.lang.Boolean isTest) throws java.rmi.RemoteException;
    public java.lang.String encryptOrDecrypt(java.lang.String tokenString, java.lang.String txt, java.lang.String salt, java.lang.Boolean isDec) throws java.rmi.RemoteException;
    public java.lang.String getCbcReport(java.lang.String tokenString, org.datacontract.schemas.kservice.model.datacontracts.ViewEnquiry enquiry, java.lang.Boolean isTest) throws java.rmi.RemoteException;
    public java.lang.String sendEnquiryRequestToCbc(java.lang.String tokenString, org.datacontract.schemas.kservice.model.datacontracts.RequestEnquiry enquiry, org.datacontract.schemas.kservice.model.datacontracts.Authentication authentication, java.lang.Boolean isTest) throws java.rmi.RemoteException;
    public java.lang.String saveHistoricalReport(java.lang.String tokenString, org.datacontract.schemas.kservice.model.datacontracts.RequestEnquiry enquiry, org.datacontract.schemas.kservice.model.CbcAuthentication cbcAuthenctication, java.lang.Boolean isTest) throws java.rmi.RemoteException;
    public void insertLog(java.lang.String token, org.datacontract.schemas.kservice.model.datacontracts.Log log, java.lang.Boolean isTest) throws java.rmi.RemoteException;
    public java.lang.String sendEmailAboutSecurityNumber(java.lang.String tokenString, java.lang.Integer userId, java.lang.Boolean isTest) throws java.rmi.RemoteException;
    public java.lang.String showReportFromXML(java.lang.String tokenString, org.datacontract.schemas.kservice.model.datacontracts.RequestEnquiry enquiry) throws java.rmi.RemoteException;
}
