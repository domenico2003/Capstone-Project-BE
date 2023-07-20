package application.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import application.entities.Argomento;
import application.entities.Gruppo;
import application.entities.Utente;
import application.exceptions.BadRequestException;
import application.exceptions.NotFoundException;
import application.payloads.GruppoPayload;
import application.repository.ArgomentoRepository;
import application.repository.GruppoRepository;
import application.repository.UtenteRepository;

@Service
public class GruppoService {

	@Autowired
	GruppoRepository gr;
	@Autowired
	ArgomentoRepository ar;
	@Autowired
	UtenteService userService;
	@Autowired
	UtenteRepository userRepo;
//CRUD gruppo

	public Gruppo findById(String id) {
		return gr.findById(UUID.fromString(id))
				.orElseThrow(() -> new NotFoundException("Gruppo con id: " + id + " non trovato!"));
	}

	public Page<Gruppo> findAll(int page, String ordinamento, int size) {
		Pageable pagina = PageRequest.of(page, size, Sort.by(ordinamento));
		return gr.findAll(pagina);
	}

	public Gruppo create(GruppoPayload body) {
		Utente fondatore = userService.findById(body.getFondatoreId());
		Gruppo gruppoCreato = new Gruppo(body.getNome(), body.getDescrizione(), fondatore, body.getImmagineGruppo());
		for (String argomento : body.getArgomenti()) {
			Argomento arg = ar.findByNomeIgnoreCase(argomento);

			if (arg == null) {
				Argomento argomentoSalvato = new Argomento(argomento);
				ar.save(argomentoSalvato);

				Argomento argomentoRicavato = ar.findByNomeIgnoreCase(argomento);
				gruppoCreato.addArgomento(argomentoRicavato);
			} else {
				gruppoCreato.addArgomento(arg);
			}
		}

		fondatore.setGruppo(gruppoCreato);
		return gr.save(gruppoCreato);
	}

	public Gruppo findByIdAndUpdate(String id, GruppoPayload body) {
		Gruppo gruppoModificato = this.findById(id);
		gruppoModificato.setNome(body.getNome());

		gruppoModificato.setDataUltimoAggiornamento(LocalDate.now());
		gruppoModificato.setDescrizione(body.getDescrizione());
		gruppoModificato.setImmagineGruppo(body.getImmagineGruppo());
		gruppoModificato.setArgomenti(new ArrayList<Argomento>());
		for (String argomento : body.getArgomenti()) {
			Argomento arg = ar.findByNomeIgnoreCase(argomento);

			if (arg == null) {
				Argomento argomentoSalvato = new Argomento(argomento);
				ar.save(argomentoSalvato);

				Argomento argomentoRicavato = ar.findByNomeIgnoreCase(argomento);
				gruppoModificato.addArgomento(argomentoRicavato);
			} else {
				gruppoModificato.addArgomento(arg);
			}
		}
		return gr.save(gruppoModificato);
	}

	public void findByIdAndDelete(String id) {
		Gruppo gruppoEliminato = this.findById(id);

		userRepo.findByGruppo(gruppoEliminato).stream().forEach((user) -> {
			user.setGruppo(null);
			userRepo.save(user);
		});
		gr.delete(gruppoEliminato);
	}

//metodi ADMIN

	public Gruppo setNuovoFondatore(String emailFondatore, String idGruppo) {
		Gruppo gruppo = this.findById(idGruppo);

		Utente fondatore = userRepo.findByEmail(emailFondatore)
				.orElseThrow(() -> new BadRequestException("Utente con email: " + emailFondatore + " non trovato"));
		gruppo.setFondatore(fondatore);
		return gr.save(gruppo);
	}

//metodi custom

	public Page<Gruppo> findLByFondatore(int page, String ordinamento, int size, String idFondatore) {
		Pageable pagina = PageRequest.of(page, size, Sort.by(ordinamento));
		return gr.findByFondatore(pagina, userService.findById(idFondatore));
	}

	public Page<Gruppo> findByNome(int page, String ordinamento, int size, String nome) {
		Pageable pagina = PageRequest.of(page, size, Sort.by(ordinamento));
		return gr.findByNomeStartingWithIgnoreCase(pagina, nome);
	};

	public Page<Gruppo> findByArgomenti(int page, String ordinamento, int size, String argomento) {
		Pageable pagina = PageRequest.of(page, size, Sort.by(ordinamento));
		List<Argomento> arg = ar.findByNomeStartingWithIgnoreCase(argomento);

		if (arg.size() == 1) {
			return gr.findByArgomenti(pagina, arg.get(0));
		} else if (arg.size() > 1) {
			throw new BadRequestException("ricerca un genere più preciso!");
		} else {
			throw new NotFoundException("genere non trovato");
		}
	};

	public Page<Gruppo> findBydataCreazione(int page, String ordinamento, int size, LocalDate da, LocalDate a) {
		Pageable pagina = PageRequest.of(page, size, Sort.by(ordinamento));
		return gr.findBydataCreazioneBetween(pagina, da, a);
	};

	public Page<Gruppo> findBydataCreazione(int page, String ordinamento, int size, LocalDate dataCreazione) {
		Pageable pagina = PageRequest.of(page, size, Sort.by(ordinamento));
		return gr.findBydataCreazione(pagina, dataCreazione);
	};

	public List<Argomento> findArgomenti() {
		return ar.findAll();
	}
}
