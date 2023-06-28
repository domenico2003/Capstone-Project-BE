package application.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUtentePayload {
	private String username;
	private String email;
	private String nome;
	private String cognome;
	private String immagineProfilo;
}
