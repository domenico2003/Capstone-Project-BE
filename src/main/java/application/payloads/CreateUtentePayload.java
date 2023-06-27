package application.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUtentePayload {
	private String username;
	private String email;
	private String password;
	private String nome;
	private String cognome;
}
