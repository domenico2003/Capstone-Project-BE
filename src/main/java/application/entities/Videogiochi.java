package application.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import application.entities.enums.Piattaforme;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonIgnoreProperties({ "responsabile", "aggiuntiHaiPreferiti", "recensioni", "sommaValutazioni" })
public class Videogiochi {
	// colonne della tabella che si genera
	@Id
	@GeneratedValue
	private UUID id;
	@Column(length = 600)
	private String copertina;
	//
	private String nome;
	@Column(length = 1000)
	private String descrizione;
	//
	@ManyToMany
	private List<Genere> generi = new ArrayList<>();
	@Enumerated(EnumType.STRING)
	private List<Piattaforme> piattaforme;
	//
	private String aziendaProprietaria;

	//
	@ManyToOne //
	private Utente responsabile;

	@OneToMany(mappedBy = "gioco", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Preferiti> aggiuntiHaiPreferiti;

	@OneToMany(mappedBy = "gioco", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Recensione> recensioni;

	private String videoTrailer;
	//
	private LocalDate dataRilascio;
	//
	private int valutazioneMedia = 0;
	private long sommaValutazioni = 0;
	// costruttore

	public Videogiochi(String nome, String copertina, String descrizione, List<Piattaforme> piattaforme,
			String aziendaProprietaria, Utente responsabile, String videoTrailer, LocalDate dataRilascio) {
		this.copertina = copertina;
		this.nome = nome;
		this.descrizione = descrizione;

		this.piattaforme = piattaforme;
		this.aziendaProprietaria = aziendaProprietaria;
		this.responsabile = responsabile;
		this.videoTrailer = videoTrailer;
		this.dataRilascio = dataRilascio;
	}

	// metodi

	public void addGeneri(Genere gen) {
		this.generi.add(gen);
	}

	public synchronized void SetSommaValutazioni(long valutazione) {
		this.sommaValutazioni += valutazione;
	}

	public synchronized void SetDiminuisciSommaValutazioni(long valutazione) {
		this.sommaValutazioni -= valutazione;
	}

	public synchronized void setValutazioneMedia() {
		int numeroRecensioni = recensioni.size() + 1;
		this.valutazioneMedia = (int) (this.getSommaValutazioni() / numeroRecensioni);
	}

}
