package application.entities;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({ "gioco" })
public class Recensione {

	@Id
	@GeneratedValue
	private UUID id;
	@ManyToOne
	private Utente utente;
	@ManyToOne
	private Videogiochi gioco;
	private int valutazione;
	private String descrizione;
	private LocalDate dataPublicazione;
	private LocalDate dataUltimoAggiornamento;

	public Recensione(Utente utente, Videogiochi gioco, int valutazione, String descrizione) {

		this.utente = utente;
		this.gioco = gioco;
		this.valutazione = valutazione;
		this.descrizione = descrizione;
		this.dataPublicazione = LocalDate.now();
		this.dataUltimoAggiornamento = LocalDate.now();
	}

}
