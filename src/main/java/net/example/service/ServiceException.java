package net.example.service;

public class ServiceException extends Exception {
    public ServiceException(String s, Exception e) {
        super(s, e);
    }

    public ServiceException(String s) {
        super(s);
    }
}
