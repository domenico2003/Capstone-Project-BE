package application.payloads;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class VideogiochiPayload {
	private String nome;
	private String copertina;
	private String descrizione;
	private List<String> generi;
	private List<String> piattaforme;
	private String aziendaProprietaria;
	private String responsabileId;
	private String videoTrailer;
	private LocalDate dataRilascio;
}
