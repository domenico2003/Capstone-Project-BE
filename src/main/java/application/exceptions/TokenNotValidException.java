package application.exceptions;

public class TokenNotValidException extends RuntimeException {
	public TokenNotValidException() {
		super("Token non valido!");
	}
}
