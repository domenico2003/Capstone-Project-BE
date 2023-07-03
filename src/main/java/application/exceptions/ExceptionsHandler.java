package application.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import application.payloads.ErrorsPayload;

@RestControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler(TokenNotValidException.class)
	public ResponseEntity<ErrorsPayload> handleBadRequest(TokenNotValidException e) {

		ErrorsPayload payload = new ErrorsPayload(e.getMessage(), new Date(), 403);

		return new ResponseEntity<ErrorsPayload>(payload, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorsPayload> handleAccessDenied(AccessDeniedException e) {

		ErrorsPayload payload = new ErrorsPayload("accesso negato,non hai l'autorizaazzione per accedere!", new Date(),
				403);

		return new ResponseEntity<ErrorsPayload>(payload, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorsPayload> handleBadRequest(BadRequestException e) {

		ErrorsPayload payload = new ErrorsPayload(e.getMessage(), new Date(), 400);

		return new ResponseEntity<ErrorsPayload>(payload, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorsPayload> handleUnauthorized(UnauthorizedException e) {

		ErrorsPayload payload = new ErrorsPayload(e.getMessage(), new Date(), 401);

		return new ResponseEntity<ErrorsPayload>(payload, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorsPayload> handleNotFound(NotFoundException e) {

		ErrorsPayload payload = new ErrorsPayload(e.getMessage(), new Date(), 404);

		return new ResponseEntity<ErrorsPayload>(payload, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorsPayload> handleGeneric(Exception e) {
		System.out.println(e);
		// e.printStackTrace();
		ErrorsPayload payload = new ErrorsPayload("Errore Generico", new Date(), 500);

		return new ResponseEntity<ErrorsPayload>(payload, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
