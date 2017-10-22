package br.edu.ifpe.acsntrs.jpa.exceptions;

public class PreexistingEntityException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8010496895318063279L;
	public PreexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public PreexistingEntityException(String message) {
        super(message);
    }
}
