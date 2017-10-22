package br.edu.ifpe.acsntrs.jpa.exceptions;

public class NonexistentEntityException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8321688938109671635L;
	public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public NonexistentEntityException(String message) {
        super(message);
    }
}
