package application.payloads;

import lombok.Data;

@Data
public class RecensionePayload {
	private String utenteId;
	private String giocoId;
	private int valutazione;
	private String descrizione;
}
