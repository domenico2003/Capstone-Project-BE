package application.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Genere;

public interface GeneriRepository extends JpaRepository<Genere, UUID> {
	List<Genere> findByNomeStartingWithIgnoreCase(String nome);

	Genere findByNomeIgnoreCase(String nome);
}
