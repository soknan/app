package org.tempuri;

public class IKserviceProxy implements org.tempuri.IKservice {
  private String _endpoint = null;
  private org.tempuri.IKservice iKservice = null;
  
  public IKserviceProxy() {
    _initIKserviceProxy();
  }
  
  public IKserviceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIKserviceProxy();
  }
  
  private void _initIKserviceProxy() {
    try {
      iKservice = (new org.tempuri.KserviceLocator()).getbasic();
      if (iKservice != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iKservice)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iKservice)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iKservice != null)
      ((javax.xml.rpc.Stub)iKservice)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.IKservice getIKservice() {
    if (iKservice == null)
      _initIKserviceProxy();
    return iKservice;
  }
  
  public org.datacontract.schemas.kservice.model.datacontracts.Authentication login(java.lang.String tokenString, org.datacontract.schemas.kservice.model.datacontracts.Authentication authentication, java.lang.Boolean isTest) throws java.rmi.RemoteException{
    if (iKservice == null)
      _initIKserviceProxy();
    return iKservice.login(tokenString, authentication, isTest);
  }
  
  public java.lang.String getConnectionString(java.lang.String tokenString, java.lang.Boolean isTest) throws java.rmi.RemoteException{
    if (iKservice == null)
      _initIKserviceProxy();
    return iKservice.getConnectionString(tokenString, isTest);
  }
  
  public java.lang.Boolean testConnection(java.lang.String tokenString, java.lang.Boolean isTest) throws java.rmi.RemoteException{
    if (iKservice == null)
      _initIKserviceProxy();
    return iKservice.testConnection(tokenString, isTest);
  }
  
  public java.lang.String encryptOrDecrypt(java.lang.String tokenString, java.lang.String txt, java.lang.String salt, java.lang.Boolean isDec) throws java.rmi.RemoteException{
    if (iKservice == null)
      _initIKserviceProxy();
    return iKservice.encryptOrDecrypt(tokenString, txt, salt, isDec);
  }
  
  public java.lang.String getCbcReport(java.lang.String tokenString, org.datacontract.schemas.kservice.model.datacontracts.ViewEnquiry enquiry, java.lang.Boolean isTest) throws java.rmi.RemoteException{
    if (iKservice == null)
      _initIKserviceProxy();
    return iKservice.getCbcReport(tokenString, enquiry, isTest);
  }
  
  public java.lang.String sendEnquiryRequestToCbc(java.lang.String tokenString, org.datacontract.schemas.kservice.model.datacontracts.RequestEnquiry enquiry, org.datacontract.schemas.kservice.model.datacontracts.Authentication authentication, java.lang.Boolean isTest) throws java.rmi.RemoteException{
    if (iKservice == null)
      _initIKserviceProxy();
    return iKservice.sendEnquiryRequestToCbc(tokenString, enquiry, authentication, isTest);
  }
  
  public java.lang.String saveHistoricalReport(java.lang.String tokenString, org.datacontract.schemas.kservice.model.datacontracts.RequestEnquiry enquiry, org.datacontract.schemas.kservice.model.CbcAuthentication cbcAuthenctication, java.lang.Boolean isTest) throws java.rmi.RemoteException{
    if (iKservice == null)
      _initIKserviceProxy();
    return iKservice.saveHistoricalReport(tokenString, enquiry, cbcAuthenctication, isTest);
  }
  
  public void insertLog(java.lang.String token, org.datacontract.schemas.kservice.model.datacontracts.Log log, java.lang.Boolean isTest) throws java.rmi.RemoteException{
    if (iKservice == null)
      _initIKserviceProxy();
    iKservice.insertLog(token, log, isTest);
  }
  
  public java.lang.String sendEmailAboutSecurityNumber(java.lang.String tokenString, java.lang.Integer userId, java.lang.Boolean isTest) throws java.rmi.RemoteException{
    if (iKservice == null)
      _initIKserviceProxy();
    return iKservice.sendEmailAboutSecurityNumber(tokenString, userId, isTest);
  }
  
  public java.lang.String showReportFromXML(java.lang.String tokenString, org.datacontract.schemas.kservice.model.datacontracts.RequestEnquiry enquiry) throws java.rmi.RemoteException{
    if (iKservice == null)
      _initIKserviceProxy();
    return iKservice.showReportFromXML(tokenString, enquiry);
  }
  
  
}