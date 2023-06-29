package application.payloads;

import lombok.Data;

@Data
public class CommentoPayload {
	private String utenteId;
	private String postId;
	private String testo;
}
