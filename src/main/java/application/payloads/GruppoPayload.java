package application.payloads;

import java.util.List;

import lombok.Data;

@Data
public class GruppoPayload {
	private String nome;
	private List<String> argomenti;
	private String descrizione;
	private String fondatoreId;
	private String immagineGruppo;
}
