package com.bolife.online.exception;

public class QexzWebException extends Exception {

    public final QexzWebError qexzWebError;

    public QexzWebException(QexzWebError qexzWebError) {
        this.qexzWebError = qexzWebError;
    }
}
