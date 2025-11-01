package com.cts.AuthService.exception;

public class CredentialsInvalidException extends RuntimeException {
	public CredentialsInvalidException(String message)
	{
		super(message);
	}
}
