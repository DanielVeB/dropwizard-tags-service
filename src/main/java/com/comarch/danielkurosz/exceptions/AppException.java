package com.comarch.danielkurosz.exceptions;

public class AppException extends Exception{
    private Integer status;

    /**
     * application specific error code
     */
    private int code;

    /**
     * link documenting the exception
     */
    private String link;

    /**
     * extra information how can it be repaired
     */
    private String advice;

    /**
     * @param status  contains the same HTTP Status code returned by the server
     * @param code    application specific error code
     * @param message message describing the error
     * @param advice  extra information how can it be repaired
     * @param link    link point to page where the error message is documented
     */
    AppException(int status, int code, String message,
                        String advice, String link) {
        super(message);
        this.status = status;
        this.code = code;
        this.advice = advice;
        this.link = link;
    }

    Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
