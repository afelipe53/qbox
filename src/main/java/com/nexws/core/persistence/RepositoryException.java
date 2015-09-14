package com.nexws.core.persistence;

public class RepositoryException extends Exception {

	private static final long serialVersionUID = 3785782248769803001L;
	private Object param;

	public RepositoryException(String message) {
		super(message);
	}

	public RepositoryException(String message, Throwable throwable){
		super(message, throwable);
	}

	public RepositoryException(String message, Object param){
		super(message);
		this.param = param;
	}

	public RepositoryException(String message, Object param, Throwable throwable){
		super(message, throwable);
		this.param = param;
	}

	public Object getParam() {
		return this.param;
	}

}