package com.ashraya.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggerService {
	 

	private static final Logger logger = LoggerFactory.getLogger(LoggerService.class);

	public void printStart(String className,String methodName) {
		logger.info(className+" starting "+ methodName);
	}
	
	public void printStart(String className, String payload, String methodName) {
		logger.info(className+" starting "+ methodName);
		logger.info("Recieved payload:: " + payload);
	}
	
	public void printEnd(String className,String methodName) {
		logger.info(className+" ending "+ methodName);
	}
}
