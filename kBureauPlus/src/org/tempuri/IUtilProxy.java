package org.tempuri;

public class IUtilProxy implements org.tempuri.IUtil {
  private String _endpoint = null;
  private org.tempuri.IUtil iUtil = null;
  
  public IUtilProxy() {
    _initIUtilProxy();
  }
  
  public IUtilProxy(String endpoint) {
    _endpoint = endpoint;
    _initIUtilProxy();
  }
  
  private void _initIUtilProxy() {
    try {
      iUtil = (new org.tempuri.UtilLocator()).getBasicHttpBinding_IUtil();
      if (iUtil != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iUtil)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iUtil)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iUtil != null)
      ((javax.xml.rpc.Stub)iUtil)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.IUtil getIUtil() {
    if (iUtil == null)
      _initIUtilProxy();
    return iUtil;
  }
  
  public java.lang.String encryptOrDecrypt(java.lang.String tokenString, java.lang.String txt, java.lang.String salt, java.lang.Boolean isDec) throws java.rmi.RemoteException{
    if (iUtil == null)
      _initIUtilProxy();
    return iUtil.encryptOrDecrypt(tokenString, txt, salt, isDec);
  }
  
  
}