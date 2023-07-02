package application.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Commenti;
import application.entities.Utente;

public interface CommentiRepository extends JpaRepository<Commenti, UUID> {
	Page<Commenti> findByAutore(Pageable pagina, Utente autore);

	List<Commenti> findByAutore(Utente autore);
}
