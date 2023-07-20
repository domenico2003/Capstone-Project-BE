package application.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Genere;
import application.entities.Utente;
import application.entities.Videogiochi;

public interface VideogiochiRepository extends JpaRepository<Videogiochi, UUID> {
	Page<Videogiochi> findByResponsabile(Pageable pagina, Utente responsabile);

	Page<Videogiochi> findByNomeStartingWithIgnoreCase(Pageable pagina, String nome);

	Page<Videogiochi> findByGeneri(Pageable pagina, Genere genere);

	Page<Videogiochi> findByAziendaProprietariaStartingWithIgnoreCase(Pageable pagina, String aziendaProprietaria);

	Page<Videogiochi> findByValutazioneMediaGreaterThanEqual(Pageable pagina, int valutazioneMedia);

	Page<Videogiochi> findByDataRilascioBetween(Pageable pagina, LocalDate da, LocalDate a);

	Page<Videogiochi> findByDataRilascio(Pageable pagina, LocalDate dataRilascio);

	List<Videogiochi> findByResponsabile(Utente responsabile);
}
