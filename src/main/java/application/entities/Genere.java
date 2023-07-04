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
@JsonIgnoreProperties({ "id" })
public class Genere {
	@Id
	@GeneratedValue
	private UUID id;
	private String nome;

	public Genere(String nome) {

		this.nome = nome;
	}
}
