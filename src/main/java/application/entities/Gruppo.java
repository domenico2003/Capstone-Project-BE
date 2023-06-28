package application.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties({ "membri", "post" })
public class Gruppo {

	@Id
	@GeneratedValue
	private UUID id;
	@Column(length = 600)
	private String immagineGruppo;
	private String nome;
	private List<String> argomenti;
	@Column(length = 1000)
	private String descrizione;
	@ManyToOne
	private Utente fondatore;
	private LocalDate dataCreazione;
	private LocalDate dataUltimoAggiornamento;

	@OneToMany
	private List<Utente> membri;

	@OneToMany
	private List<Post> post;

	public Gruppo(String nome, List<String> argomenti, String descrizione, Utente fondatore, String immagineGruppo) {
		this.immagineGruppo = immagineGruppo;
		this.nome = nome;
		this.argomenti = argomenti;
		this.descrizione = descrizione;
		this.fondatore = fondatore;
		this.dataCreazione = LocalDate.now();
		this.dataUltimoAggiornamento = LocalDate.now();
	}

}
