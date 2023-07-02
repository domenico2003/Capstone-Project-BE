package application.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Gruppo;
import application.entities.Utente;

public interface GruppoRepository extends JpaRepository<Gruppo, UUID> {
	Page<Gruppo> findByFondatore(Pageable pagina, Utente fondatore);

	List<Gruppo> findByFondatore(Utente fondatore);

}
