package com.ashraya.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerService {

    private String className;
    private static final Logger logger = LoggerFactory.getLogger(LoggerService.class);

    public LoggerService(String className) {
        this.className = className;
    }

    public static LoggerService createLogger(String name) {
        return new LoggerService(name);
    }

    public void printStart(String methodName) {
        logger.info(className + " starting " + methodName);
    }

    public void printStart(String payload, String methodName) {
        logger.info(className + " starting " + methodName);
        logger.info("Recieved payload:: " + payload);
    }

    public void printEnd(String methodName) {
        logger.info(className + " ending " + methodName);
    }
}
