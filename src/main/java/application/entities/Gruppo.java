package application.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@JsonIgnoreProperties({ "membri", "post" })
public class Gruppo {

	@Id
	@GeneratedValue
	private UUID id;
	@Column(length = 600)
	private String immagineGruppo;
	private String nome;
	@ManyToMany
	private List<Argomento> argomenti = new ArrayList<Argomento>();
	@Column(length = 1000)
	private String descrizione;

	@ManyToOne //
	private Utente fondatore;
	private LocalDate dataCreazione;
	private LocalDate dataUltimoAggiornamento;

	@OneToMany(mappedBy = "gruppo")
	private List<Utente> membri;

	@OneToMany(mappedBy = "gruppo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Post> post;

	public Gruppo(String nome, String descrizione, Utente fondatore, String immagineGruppo) {
		this.immagineGruppo = immagineGruppo;
		this.nome = nome;

		this.descrizione = descrizione;
		this.fondatore = fondatore;
		this.dataCreazione = LocalDate.now();
		this.dataUltimoAggiornamento = LocalDate.now();
	}

	public void addArgomento(Argomento arg) {
		this.argomenti.add(arg);
	}

}
