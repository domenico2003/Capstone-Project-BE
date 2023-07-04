package application.services;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import application.entities.Recensione;
import application.entities.Videogiochi;
import application.exceptions.NotFoundException;
import application.payloads.RecensionePayload;
import application.repository.RecensioneRepository;
import application.repository.VideogiochiRepository;

@Service
public class RecensioneService {

	@Autowired
	RecensioneRepository recRep;
	@Autowired
	VideogiochiRepository giocoRepo;
	@Autowired
	UtenteService userService;

	@Autowired
	VideogiochiService videogiocoService;

// CRUD per recensione

	public Recensione findById(String id) {
		return recRep.findById(UUID.fromString(id))
				.orElseThrow(() -> new NotFoundException("post con id: " + id + " non trovato!"));
	}

	public Page<Recensione> findAll(int page, String ordinamento) {
		Pageable pagina = PageRequest.of(page, 5, Sort.by(ordinamento));
		return recRep.findAll(pagina);
	}

	public Recensione create(RecensionePayload body) {
		Videogiochi videogiocoRecensito = videogiocoService.findById(body.getGiocoId());
		videogiocoRecensito.SetSommaValutazioni(body.getValutazione());
		videogiocoRecensito.setValutazioneMedia();
		giocoRepo.save(videogiocoRecensito);
		Recensione recensioneCreata = new Recensione(userService.findById(body.getUtenteId()),
				videogiocoService.findById(body.getGiocoId()), body.getValutazione(), body.getDescrizione());
		return recRep.save(recensioneCreata);
	}

	public Recensione findByIdAndUpdate(String id, RecensionePayload body) {
		Recensione recensioneModificata = this.findById(id);

		Videogiochi videogiocoRecensito = videogiocoService.findById(body.getGiocoId());
		videogiocoRecensito.SetDiminuisciSommaValutazioni(recensioneModificata.getValutazione());
		videogiocoRecensito.SetSommaValutazioni(body.getValutazione());
		videogiocoRecensito.setValutazioneMedia();
		giocoRepo.save(videogiocoRecensito);

		recensioneModificata.setDataUltimoAggiornamento(LocalDate.now());
		recensioneModificata.setValutazione(body.getValutazione());
		recensioneModificata.setDescrizione(body.getDescrizione());
		return recRep.save(recensioneModificata);
	}

	public void findByIdAndDelete(String id) {
		int valutazione = 0;
		String idVideogioco = "";
		Recensione recensioneEliminata = this.findById(id);
		valutazione = recensioneEliminata.getValutazione();
		idVideogioco = recensioneEliminata.getGioco().getId().toString();
		recRep.delete(recensioneEliminata);

		aggiornaVideogiocoDelete(idVideogioco, valutazione);

		System.out.println("deleted");

	}

//metodi custom

	public void aggiornaVideogiocoDelete(String id, int valutazione) {
		Videogiochi videogioco = videogiocoService.findById(id);
		videogioco.SetDiminuisciSommaValutazioni(valutazione);
		videogioco.setValutazioneMedia();
		giocoRepo.save(videogioco);
	}

	public Page<Recensione> findByGioco(String idGioco, int page, String order) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(order));
		return recRep.findByGioco(pagina, videogiocoService.findById(idGioco));
	}
}
