package com.sc.minitweater.mvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class NotValidTokenException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6941918754095530925L;

	public NotValidTokenException()
	{
		super();
	}
	
}
