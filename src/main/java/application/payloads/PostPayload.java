package application.payloads;

import lombok.Data;

@Data
public class PostPayload {
	private String contenuto;
	private String gruppoId;
	private String utenteCheLoHaPublicatoId;
	private String immagine;
}
