package br.edu.ifpe.acsntrs.jpa.exceptions;

public class RollbackFailureException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4552443566315441700L;
	public RollbackFailureException(String message, Throwable cause) {
        super(message, cause);
    }
    public RollbackFailureException(String message) {
        super(message);
    }
}
