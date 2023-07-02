package application.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Utente;
import application.entities.Videogiochi;

public interface VideogiochiRepository extends JpaRepository<Videogiochi, UUID> {
	Page<Videogiochi> findByResponsabile(Pageable pagina, Utente responsabile);

	List<Videogiochi> findByResponsabile(Utente responsabile);
}
