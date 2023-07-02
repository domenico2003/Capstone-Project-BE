package application.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Recensione;
import application.entities.Utente;

public interface RecensioneRepository extends JpaRepository<Recensione, UUID> {
	Page<Recensione> findByUtente(Pageable pagina, Utente utente);

	List<Recensione> findByUtente(Utente utente);
}
