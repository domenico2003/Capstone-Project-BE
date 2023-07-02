package application.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({ "gruppo", "commenti" })
public class Post {
	@Id
	@GeneratedValue
	private UUID id;
	@Column(length = 600)
	private String immagine;
	@Column(length = 1000)
	private String contenuto;

	@ManyToOne
	private Gruppo gruppo;

	private long numeroMiPiace = 0;

	@ManyToOne
	private Utente utenteCheLoHaPublicato;

	private LocalDate dataCreazione;
	private LocalDate dataUltimoAggiornamento;

	@OneToMany(mappedBy = "postCommentato", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Commenti> commenti;

	public Post(String contenuto, Gruppo gruppo, Utente utenteCheLoHaPublicato, String immagine) {
		super();
		this.contenuto = contenuto;
		this.gruppo = gruppo;
		this.immagine = immagine;
		this.utenteCheLoHaPublicato = utenteCheLoHaPublicato;
		this.dataCreazione = LocalDate.now();
		this.dataUltimoAggiornamento = LocalDate.now();
	}

	public void aggiungiMiPiace() {
		this.numeroMiPiace += 1;
	}
}
