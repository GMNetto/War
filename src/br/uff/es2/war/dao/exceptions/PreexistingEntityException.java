package br.uff.es2.war.dao.exceptions;

public class PreexistingEntityException extends Exception {
    public PreexistingEntityException(String message, Throwable cause) {
	super(message, cause);
    }

    public PreexistingEntityException(String message) {
	super(message);
    }
}
