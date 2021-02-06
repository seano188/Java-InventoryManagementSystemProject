package sg.edu.iss.team8ca.service;

public class ResourceNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3961766922902813608L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
