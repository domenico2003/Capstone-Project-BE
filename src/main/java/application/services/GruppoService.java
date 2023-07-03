package application.services;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import application.entities.Gruppo;
import application.entities.Utente;
import application.exceptions.BadRequestException;
import application.exceptions.NotFoundException;
import application.payloads.GruppoPayload;
import application.repository.GruppoRepository;
import application.repository.UtenteRepository;

@Service
public class GruppoService {

	@Autowired
	GruppoRepository gr;

	@Autowired
	UtenteService userService;
	@Autowired
	UtenteRepository userRepo;
//CRUD gruppo

	public Gruppo findById(String id) {
		return gr.findById(UUID.fromString(id))
				.orElseThrow(() -> new NotFoundException("Gruppo con id: " + id + " non trovato!"));
	}

	public Page<Gruppo> findAll(int page, String ordinamento) {
		Pageable pagina = PageRequest.of(page, 5, Sort.by(ordinamento));
		return gr.findAll(pagina);
	}

	public Gruppo create(GruppoPayload body) {
		Utente fondatore = userService.findById(body.getFondatoreId());
		Gruppo gruppoCreato = new Gruppo(body.getNome(), body.getArgomenti(), body.getDescrizione(), fondatore,
				body.getImmagineGruppo());
		fondatore.setGruppo(gruppoCreato);
		return gr.save(gruppoCreato);
	}

	public Gruppo findByIdAndUpdate(String id, GruppoPayload body) {
		Gruppo gruppoModificato = this.findById(id);
		gruppoModificato.setNome(body.getNome());
		gruppoModificato.setArgomenti(body.getArgomenti());
		gruppoModificato.setDataUltimoAggiornamento(LocalDate.now());
		gruppoModificato.setDescrizione(body.getDescrizione());
		gruppoModificato.setImmagineGruppo(body.getImmagineGruppo());

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

	public Page<Gruppo> findLByFondatore(int page, String ordinamento, String idFondatore) {
		Pageable pagina = PageRequest.of(page, 5, Sort.by(ordinamento));
		return gr.findByFondatore(pagina, userService.findById(idFondatore));
	}
}
