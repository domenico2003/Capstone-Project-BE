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
import application.exceptions.NotFoundException;
import application.payloads.GruppoPayload;
import application.repository.GruppoRepository;

@Service
public class GruppoService {

	@Autowired
	GruppoRepository gr;

	@Autowired
	UtenteService userService;
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
		Gruppo gruppoCreato = new Gruppo(body.getNome(), body.getArgomenti(), body.getDescrizione(),
				userService.findById(body.getFondatoreId()), body.getImmagineGruppo());
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

		gr.delete(gruppoEliminato);
	}

//metodi custom
}
