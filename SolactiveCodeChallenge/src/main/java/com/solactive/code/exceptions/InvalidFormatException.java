package com.solactive.code.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class InvalidFormatException extends RuntimeException {

	private static final long serialVersionUID = 4766381541739780889L;

	public InvalidFormatException(String message) {
		super(message);
	}
	
	public InvalidFormatException(String message, Throwable throwable) {
		super(message,throwable);
	}
	
	
}
