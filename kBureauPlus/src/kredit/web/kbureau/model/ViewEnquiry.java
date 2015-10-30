/**
 * ViewEnquiry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package kredit.web.kbureau.model;

public class ViewEnquiry {
    private java.lang.Integer id;

    private java.lang.String refNumber;

    public ViewEnquiry() {
    }

    public ViewEnquiry(
           java.lang.Integer id,
           java.lang.String refNumber) {
           this.id = id;
           this.refNumber = refNumber;
    }


    /**
     * Gets the id value for this ViewEnquiry.
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }


    /**
     * Sets the id value for this ViewEnquiry.
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }


    /**
     * Gets the refNumber value for this ViewEnquiry.
     * 
     * @return refNumber
     */
    public java.lang.String getRefNumber() {
        return refNumber;
    }


    /**
     * Sets the refNumber value for this ViewEnquiry.
     * 
     * @param refNumber
     */
    public void setRefNumber(java.lang.String refNumber) {
        this.refNumber = refNumber;
    }
}
