package application.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Argomento;

public interface ArgomentoRepository extends JpaRepository<Argomento, UUID> {
	Argomento findByNomeIgnoreCase(String nome);

	List<Argomento> findByNomeStartingWithIgnoreCase(String nome);
}
