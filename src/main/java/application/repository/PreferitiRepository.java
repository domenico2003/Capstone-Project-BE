package application.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Preferiti;
import application.entities.Utente;
import application.entities.Videogiochi;

public interface PreferitiRepository extends JpaRepository<Preferiti, UUID> {
	Page<Preferiti> findByUtente(Pageable page, Utente utente);

	List<Preferiti> findByUtenteAndGioco(Utente utente, Videogiochi gioco);

}
