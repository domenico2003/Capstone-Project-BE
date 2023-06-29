package application.payloads;

import lombok.Data;

@Data
public class UpdateUtentePayload {
	private String username;
	private String email;
	private String nome;
	private String cognome;
	private String immagineProfilo;
}
