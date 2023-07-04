package application.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({ "id", "gruppi" })
public class Argomento {

	@Id
	@GeneratedValue
	private UUID id;
	private String nome;

	public Argomento(String nome) {

		this.nome = nome;
	}

}
