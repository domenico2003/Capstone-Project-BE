package application.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Preferiti;
import application.entities.Utente;
import application.entities.Videogiochi;

public interface PreferitiRepository extends JpaRepository<Preferiti, UUID> {
	Page<Preferiti> findByUtente(Pageable page, Utente utente);

	Optional<Preferiti> findByUtenteAndGioco(Utente utente, Videogiochi gioco);

}
