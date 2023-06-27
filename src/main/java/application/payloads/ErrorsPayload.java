package application.payloads;

import java.util.Date;

import lombok.Getter;


@Getter
public class ErrorsPayload {
	private String message;
	private Date timestamp;
	private int internalCode;

	public ErrorsPayload(String message, Date timestamp, int internalCode) {
		super();
		this.message = message;
		this.timestamp = timestamp;
		this.internalCode = internalCode;
	}
}