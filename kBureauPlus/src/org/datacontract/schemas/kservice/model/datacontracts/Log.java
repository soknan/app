/**
 * Log.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas.kservice.model.datacontracts;

public class Log  implements java.io.Serializable {
    private java.lang.Integer duration;

    private java.lang.String exception;

    private java.lang.String level;

    private java.lang.String logger;

    private java.lang.String message;

    private java.lang.String subBranchCode;

    private java.lang.String thread;

    public Log() {
    }

    public Log(
           java.lang.Integer duration,
           java.lang.String exception,
           java.lang.String level,
           java.lang.String logger,
           java.lang.String message,
           java.lang.String subBranchCode,
           java.lang.String thread) {
           this.duration = duration;
           this.exception = exception;
           this.level = level;
           this.logger = logger;
           this.message = message;
           this.subBranchCode = subBranchCode;
           this.thread = thread;
    }


    /**
     * Gets the duration value for this Log.
     * 
     * @return duration
     */
    public java.lang.Integer getDuration() {
        return duration;
    }


    /**
     * Sets the duration value for this Log.
     * 
     * @param duration
     */
    public void setDuration(java.lang.Integer duration) {
        this.duration = duration;
    }


    /**
     * Gets the exception value for this Log.
     * 
     * @return exception
     */
    public java.lang.String getException() {
        return exception;
    }


    /**
     * Sets the exception value for this Log.
     * 
     * @param exception
     */
    public void setException(java.lang.String exception) {
        this.exception = exception;
    }


    /**
     * Gets the level value for this Log.
     * 
     * @return level
     */
    public java.lang.String getLevel() {
        return level;
    }


    /**
     * Sets the level value for this Log.
     * 
     * @param level
     */
    public void setLevel(java.lang.String level) {
        this.level = level;
    }


    /**
     * Gets the logger value for this Log.
     * 
     * @return logger
     */
    public java.lang.String getLogger() {
        return logger;
    }


    /**
     * Sets the logger value for this Log.
     * 
     * @param logger
     */
    public void setLogger(java.lang.String logger) {
        this.logger = logger;
    }


    /**
     * Gets the message value for this Log.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this Log.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the subBranchCode value for this Log.
     * 
     * @return subBranchCode
     */
    public java.lang.String getSubBranchCode() {
        return subBranchCode;
    }


    /**
     * Sets the subBranchCode value for this Log.
     * 
     * @param subBranchCode
     */
    public void setSubBranchCode(java.lang.String subBranchCode) {
        this.subBranchCode = subBranchCode;
    }


    /**
     * Gets the thread value for this Log.
     * 
     * @return thread
     */
    public java.lang.String getThread() {
        return thread;
    }


    /**
     * Sets the thread value for this Log.
     * 
     * @param thread
     */
    public void setThread(java.lang.String thread) {
        this.thread = thread;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Log)) return false;
        Log other = (Log) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.duration==null && other.getDuration()==null) || 
             (this.duration!=null &&
              this.duration.equals(other.getDuration()))) &&
            ((this.exception==null && other.getException()==null) || 
             (this.exception!=null &&
              this.exception.equals(other.getException()))) &&
            ((this.level==null && other.getLevel()==null) || 
             (this.level!=null &&
              this.level.equals(other.getLevel()))) &&
            ((this.logger==null && other.getLogger()==null) || 
             (this.logger!=null &&
              this.logger.equals(other.getLogger()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.subBranchCode==null && other.getSubBranchCode()==null) || 
             (this.subBranchCode!=null &&
              this.subBranchCode.equals(other.getSubBranchCode()))) &&
            ((this.thread==null && other.getThread()==null) || 
             (this.thread!=null &&
              this.thread.equals(other.getThread())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDuration() != null) {
            _hashCode += getDuration().hashCode();
        }
        if (getException() != null) {
            _hashCode += getException().hashCode();
        }
        if (getLevel() != null) {
            _hashCode += getLevel().hashCode();
        }
        if (getLogger() != null) {
            _hashCode += getLogger().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getSubBranchCode() != null) {
            _hashCode += getSubBranchCode().hashCode();
        }
        if (getThread() != null) {
            _hashCode += getThread().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Log.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/kService.Model.DataContracts", "Log"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("duration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/kService.Model.DataContracts", "Duration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exception");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/kService.Model.DataContracts", "Exception"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("level");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/kService.Model.DataContracts", "Level"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("logger");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/kService.Model.DataContracts", "Logger"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/kService.Model.DataContracts", "Message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subBranchCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/kService.Model.DataContracts", "SubBranchCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("thread");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/kService.Model.DataContracts", "Thread"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
