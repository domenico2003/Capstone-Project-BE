package application.entities;

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
@JsonIgnoreProperties({ "utente" })
public class Preferiti {

	@Id
	@GeneratedValue
	private UUID id;
	@ManyToOne
	private Utente utente;
	@ManyToOne
	private Videogiochi gioco;

	public Preferiti(Utente utente, Videogiochi gioco) {
		super();
		this.utente = utente;
		this.gioco = gioco;
	}

}
