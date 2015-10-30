/**
 * RequestEnquiry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas.kservice.model.datacontracts;

public class RequestEnquiry  implements java.io.Serializable {
    private java.lang.String applicantType;

    private java.lang.String fund;

    private java.lang.String loanId;

    private java.lang.String product;

    private java.lang.String purpose;

    private java.lang.String refNumber;

    private java.lang.String request;

    private java.lang.String response;

    public RequestEnquiry() {
    }

    public RequestEnquiry(
           java.lang.String applicantType,
           java.lang.String fund,
           java.lang.String loanId,
           java.lang.String product,
           java.lang.String purpose,
           java.lang.String refNumber,
           java.lang.String request,
           java.lang.String response) {
           this.applicantType = applicantType;
           this.fund = fund;
           this.loanId = loanId;
           this.product = product;
           this.purpose = purpose;
           this.refNumber = refNumber;
           this.request = request;
           this.response = response;
    }


    /**
     * Gets the applicantType value for this RequestEnquiry.
     * 
     * @return applicantType
     */
    public java.lang.String getApplicantType() {
        return applicantType;
    }


    /**
     * Sets the applicantType value for this RequestEnquiry.
     * 
     * @param applicantType
     */
    public void setApplicantType(java.lang.String applicantType) {
        this.applicantType = applicantType;
    }


    /**
     * Gets the fund value for this RequestEnquiry.
     * 
     * @return fund
     */
    public java.lang.String getFund() {
        return fund;
    }


    /**
     * Sets the fund value for this RequestEnquiry.
     * 
     * @param fund
     */
    public void setFund(java.lang.String fund) {
        this.fund = fund;
    }


    /**
     * Gets the loanId value for this RequestEnquiry.
     * 
     * @return loanId
     */
    public java.lang.String getLoanId() {
        return loanId;
    }


    /**
     * Sets the loanId value for this RequestEnquiry.
     * 
     * @param loanId
     */
    public void setLoanId(java.lang.String loanId) {
        this.loanId = loanId;
    }


    /**
     * Gets the product value for this RequestEnquiry.
     * 
     * @return product
     */
    public java.lang.String getProduct() {
        return product;
    }


    /**
     * Sets the product value for this RequestEnquiry.
     * 
     * @param product
     */
    public void setProduct(java.lang.String product) {
        this.product = product;
    }


    /**
     * Gets the purpose value for this RequestEnquiry.
     * 
     * @return purpose
     */
    public java.lang.String getPurpose() {
        return purpose;
    }


    /**
     * Sets the purpose value for this RequestEnquiry.
     * 
     * @param purpose
     */
    public void setPurpose(java.lang.String purpose) {
        this.purpose = purpose;
    }


    /**
     * Gets the refNumber value for this RequestEnquiry.
     * 
     * @return refNumber
     */
    public java.lang.String getRefNumber() {
        return refNumber;
    }


    /**
     * Sets the refNumber value for this RequestEnquiry.
     * 
     * @param refNumber
     */
    public void setRefNumber(java.lang.String refNumber) {
        this.refNumber = refNumber;
    }


    /**
     * Gets the request value for this RequestEnquiry.
     * 
     * @return request
     */
    public java.lang.String getRequest() {
        return request;
    }


    /**
     * Sets the request value for this RequestEnquiry.
     * 
     * @param request
     */
    public void setRequest(java.lang.String request) {
        this.request = request;
    }


    /**
     * Gets the response value for this RequestEnquiry.
     * 
     * @return response
     */
    public java.lang.String getResponse() {
        return response;
    }


    /**
     * Sets the response value for this RequestEnquiry.
     * 
     * @param response
     */
    public void setResponse(java.lang.String response) {
        this.response = response;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RequestEnquiry)) return false;
        RequestEnquiry other = (RequestEnquiry) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.applicantType==null && other.getApplicantType()==null) || 
             (this.applicantType!=null &&
              this.applicantType.equals(other.getApplicantType()))) &&
            ((this.fund==null && other.getFund()==null) || 
             (this.fund!=null &&
              this.fund.equals(other.getFund()))) &&
            ((this.loanId==null && other.getLoanId()==null) || 
             (this.loanId!=null &&
              this.loanId.equals(other.getLoanId()))) &&
            ((this.product==null && other.getProduct()==null) || 
             (this.product!=null &&
              this.product.equals(other.getProduct()))) &&
            ((this.purpose==null && other.getPurpose()==null) || 
             (this.purpose!=null &&
              this.purpose.equals(other.getPurpose()))) &&
            ((this.refNumber==null && other.getRefNumber()==null) || 
             (this.refNumber!=null &&
              this.refNumber.equals(other.getRefNumber()))) &&
            ((this.request==null && other.getRequest()==null) || 
             (this.request!=null &&
              this.request.equals(other.getRequest()))) &&
            ((this.response==null && other.getResponse()==null) || 
             (this.response!=null &&
              this.response.equals(other.getResponse())));
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
        if (getApplicantType() != null) {
            _hashCode += getApplicantType().hashCode();
        }
        if (getFund() != null) {
            _hashCode += getFund().hashCode();
        }
        if (getLoanId() != null) {
            _hashCode += getLoanId().hashCode();
        }
        if (getProduct() != null) {
            _hashCode += getProduct().hashCode();
        }
        if (getPurpose() != null) {
            _hashCode += getPurpose().hashCode();
        }
        if (getRefNumber() != null) {
            _hashCode += getRefNumber().hashCode();
        }
        if (getRequest() != null) {
            _hashCode += getRequest().hashCode();
        }
        if (getResponse() != null) {
            _hashCode += getResponse().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RequestEnquiry.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/kService.Model.DataContracts", "RequestEnquiry"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("applicantType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/kService.Model.DataContracts", "ApplicantType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fund");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/kService.Model.DataContracts", "Fund"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/kService.Model.DataContracts", "LoanId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("product");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/kService.Model.DataContracts", "Product"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("purpose");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/kService.Model.DataContracts", "Purpose"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/kService.Model.DataContracts", "RefNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("request");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/kService.Model.DataContracts", "Request"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("response");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/kService.Model.DataContracts", "Response"));
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
