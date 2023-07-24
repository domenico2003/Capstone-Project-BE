package application.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import application.entities.Gruppo;
import application.entities.Utente;
import application.entities.Videogiochi;
import application.entities.enums.UtenteRuoli;
import application.exceptions.BadRequestException;
import application.exceptions.NotFoundException;
import application.payloads.CreateUtentePayload;
import application.payloads.UpdateUtentePayload;
import application.payloads.UtenteDettaglio;
import application.repository.CommentiRepository;
import application.repository.GruppoRepository;
import application.repository.RecensioneRepository;
import application.repository.UtenteRepository;
import application.repository.VideogiochiRepository;

@Service
public class UtenteService {
	@Autowired
	UtenteRepository utenteRepo;
	@Autowired
	GruppoRepository gruppoRepo;
	@Autowired
	VideogiochiRepository giocoRepo;
	@Autowired
	RecensioneRepository receRepo;
	@Autowired
	CommentiRepository commRepo;

// CRUD utente

	// creo l' utente
	public Utente create(CreateUtentePayload payload) {

		utenteRepo.findByEmail(payload.getEmail()).ifPresent(user -> {
			throw new BadRequestException("Email " + user.getEmail() + " già utilizzata!");
		});

		Utente u = new Utente(payload.getEmail(), payload.getPassword(), payload.getNome(), payload.getCognome(),
				payload.getUsername());

		return utenteRepo.save(u);
	}

	public Page<Utente> findAll(int page, String ordinamento) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));
		return utenteRepo.findAll(pagina);
	}

	public Utente findByIdAndUpadate(String id, UpdateUtentePayload body) {
		Utente u = this.findById(id);
		if (!u.getEmail().equals(body.getEmail()))
			utenteRepo.findByEmail(body.getEmail()).ifPresent(user -> {
				throw new BadRequestException("Email " + user.getEmail() + " già utilizzata!");
			});
		u.setUsername(body.getUsername());
		u.setEmail(body.getEmail());
		u.setNome(body.getNome());
		u.setCognome(body.getCognome());
		u.setImmagineProfilo(body.getImmagineProfilo());
		return utenteRepo.save(u);

	}

	public void findByIdAndDelete(String id) {
		Utente u = this.findById(id);
		// gruppo
		gruppoRepo.findByFondatore(u).stream().forEach((group) -> {
			group.setFondatore(null);
			gruppoRepo.save(group);
		});
		// videogioco
		giocoRepo.findByResponsabile(u).stream().forEach((gioco) -> {
			gioco.setResponsabile(null);
			giocoRepo.save(gioco);
		});
		// recensione
		receRepo.findByUtente(u).stream().forEach((rece) -> {
			rece.setUtente(null);
			receRepo.save(rece);
		});
		// commento
		commRepo.findByAutore(u).stream().forEach((comm) -> {
			comm.setAutore(null);
			commRepo.save(comm);
		});
		utenteRepo.delete(u);

	}

	// cerco utente con id
	public Utente findById(String id) {
		return utenteRepo.findById(UUID.fromString(id))
				.orElseThrow(() -> new NotFoundException("Utente con id: " + id + " non trovato!"));
	}

	public UtenteDettaglio findByIdDetails(String id) {

		Utente utenteTrovato = this.findById(id);

		return new UtenteDettaglio(utenteTrovato.getId(), utenteTrovato.getEmail(), utenteTrovato.getImmagineProfilo(),
				utenteTrovato.getNome(), utenteTrovato.getCognome(), utenteTrovato.getUsername(),
				utenteTrovato.getRuolo(), utenteTrovato.getVideogiochiAggiuntiAlSito(), utenteTrovato.getGruppo());
	}

// metodi custom
	public Utente findByEmail(String email) {
		return this.utenteRepo.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("Utente con " + email + " non trovato!"));
	}

	public Page<Utente> findByGruppo(String idGruppo, int page, String order) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(order));
		return utenteRepo.findByGruppo(pagina, gruppoRepo.findById(UUID.fromString(idGruppo))
				.orElseThrow(() -> new BadRequestException("Gruppo con id: " + idGruppo + " non trovato!")));
	};

	public Page<Utente> findByNome(String nome, int page, String order) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(order));
		return utenteRepo.findByNomeStartingWithIgnoreCase(pagina, nome);
	};

	public Page<Utente> findByCognome(String cognome, int page, String order) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(order));
		return utenteRepo.findByCognomeStartingWithIgnoreCase(pagina, cognome);
	};

	public Page<Utente> findByUsername(String username, int page, String order) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(order));
		return utenteRepo.findByUsernameStartingWithIgnoreCase(pagina, username);
	};

// metodi ADMIN	
	public void aggiungiAdmin(String email) {
		Utente found = this.findByEmail(email);
		found.setRuolo(UtenteRuoli.ADMIN);
		utenteRepo.save(found);
	}

	public void aggiungiGameCreator(String email) {
		Utente found = this.findByEmail(email);
		found.setRuolo(UtenteRuoli.GAME_CREATOR);
		utenteRepo.save(found);
	}

	public Videogiochi aggiungiResponsabileAVideogioco(String videogiocoId, String emailNuovoResponsabile) {
		Videogiochi giocoDaModificare = giocoRepo.findById(UUID.fromString(videogiocoId))
				.orElseThrow(() -> new BadRequestException("videogioco con id: " + videogiocoId + " non trovato!"));

		Utente responabile = this.findByEmail(emailNuovoResponsabile);
		giocoDaModificare.setResponsabile(responabile);

		return giocoRepo.save(giocoDaModificare);

	}

// metodi USER

	public void uniscitiAGruppo(String id, String gruppoId) {
		Utente found = this.findById(id);

		Gruppo gruppo = gruppoRepo.findById(UUID.fromString(gruppoId))
				.orElseThrow(() -> new BadRequestException("Gruppo con id: " + gruppoId + " non trovato!"));
		found.setGruppo(gruppo);
		utenteRepo.save(found);
	}

	public void abbandonaGruppo(String id) {
		Utente found = this.findById(id);
		Gruppo gruppoAbbandonato = gruppoRepo.findById((found.getGruppo().getId()))
				.orElseThrow(() -> new BadRequestException(
						"Gruppo con id: " + found.getGruppo().getId().toString() + " non trovato!"));
		if (gruppoAbbandonato.getFondatore() != null) {
			if (gruppoAbbandonato.getFondatore().getId() == found.getId()) {
				gruppoAbbandonato.setFondatore(null);
				gruppoRepo.save(gruppoAbbandonato);
			}
		}
		found.setGruppo(null);
		utenteRepo.save(found);
	}
}
