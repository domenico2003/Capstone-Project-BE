package application.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Gruppo;
import application.entities.Utente;

public interface UtenteRepository extends JpaRepository<Utente, UUID> {
	Optional<Utente> findByEmail(String email);

	Page<Utente> findByGruppo(Pageable pagina, Gruppo gruppo);

	Page<Utente> findByNomeStartingWithIgnoreCase(Pageable pagina, String nome);

	Page<Utente> findByCognomeStartingWithIgnoreCase(Pageable pagina, String cognome);

	Page<Utente> findByUsernameStartingWithIgnoreCase(Pageable pagina, String username);

	List<Utente> findByGruppo(Gruppo gruppo);
}
