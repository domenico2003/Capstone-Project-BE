package application.payloads;

import java.util.List;
import java.util.UUID;

import application.entities.Gruppo;
import application.entities.Videogiochi;
import application.entities.enums.UtenteRuoli;
import lombok.Data;

@Data

public class UtenteDettaglio {

	private UUID id;
	private String email;

	private String immagineProfilo;
	private String nome;
	private String cognome;
	private String username;

	private UtenteRuoli ruolo;

	private List<Videogiochi> videogiochiAggiuntiAlSito;

	private Gruppo gruppo;

	public UtenteDettaglio(UUID id2, String email2, String immagineProfilo2, String nome2, String cognome2,
			String username2, UtenteRuoli ruolo2, List<Videogiochi> videogiochiAggiuntiAlSito2, Gruppo gruppo2) {
		this.id = id2;
		this.email = email2;
		this.immagineProfilo = immagineProfilo2;
		this.nome = nome2;
		this.cognome = cognome2;
		this.username = username2;
		this.ruolo = ruolo2;
		this.videogiochiAggiuntiAlSito = videogiochiAggiuntiAlSito2;

		this.gruppo = gruppo2;
	}
}
