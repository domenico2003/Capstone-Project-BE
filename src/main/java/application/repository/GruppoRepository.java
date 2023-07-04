package application.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Argomento;
import application.entities.Gruppo;
import application.entities.Utente;

public interface GruppoRepository extends JpaRepository<Gruppo, UUID> {
	Page<Gruppo> findByFondatore(Pageable pagina, Utente fondatore);

	Page<Gruppo> findByNomeStartingWithIgnoreCase(Pageable pagina, String nome);

	Page<Gruppo> findByArgomenti(Pageable pagina, Argomento argomento);

	Page<Gruppo> findBydataCreazioneBetween(Pageable pagina, LocalDate da, LocalDate a);

	Page<Gruppo> findBydataCreazione(Pageable pagina, LocalDate dataCreazione);

	List<Gruppo> findByFondatore(Utente fondatore);

}
