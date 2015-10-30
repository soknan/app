/**
 * Log.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package kredit.web.kbureau.model;

public class Log {
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
}
