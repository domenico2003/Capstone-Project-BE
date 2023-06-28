package application.entities;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({ "postCommentato" })
public class Commenti {

	@Id
	@GeneratedValue
	private UUID id;
	@Column(length = 1000)
	private String testo;

	@ManyToOne
	private Utente autore;

	private LocalDate dataCreazione;
	private LocalDate dataUltimoAggiornamento;

	@ManyToOne
	private Post postCommentato;

	public Commenti(String testo, Utente autore, Post postCommentato) {
		this.dataCreazione = LocalDate.now();
		this.dataUltimoAggiornamento = LocalDate.now();
		this.testo = testo;
		this.autore = autore;
		this.postCommentato = postCommentato;
	}

}
