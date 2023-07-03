package application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.entities.Preferiti;
import application.exceptions.BadRequestException;
import application.exceptions.NotFoundException;
import application.payloads.PreferitiPayload;
import application.repository.PreferitiRepository;

@Service
public class PreferitiService {

	@Autowired
	PreferitiRepository prefrep;

	@Autowired
	UtenteService userService;

	@Autowired
	VideogiochiService videogiochiService;

// CRUD per Preferiti

	public Preferiti findById(String id) {
		return prefrep.findById(UUID.fromString(id))
				.orElseThrow(() -> new NotFoundException("GiocoPreferito con id: " + id + " non trovato!"));
	}

	public Preferiti create(PreferitiPayload body) {
		List<Preferiti> pref = prefrep.findByUtenteAndGioco(userService.findById(body.getUtenteId()),
				videogiochiService.findById(body.getGiocoId()));

		if (pref.isEmpty()) {
			Preferiti preferitoCreato = new Preferiti(userService.findById(body.getUtenteId()),
					videogiochiService.findById(body.getGiocoId()));
			return prefrep.save(preferitoCreato);
		} else {
			throw new BadRequestException("videogioco già presente tra i preferiti");
		}
	}

	public void findByIdAndDelete(String id) {
		Preferiti preferitoEliminato = this.findById(id);

		prefrep.delete(preferitoEliminato);
	}
//metodi custom
//	Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));
}
