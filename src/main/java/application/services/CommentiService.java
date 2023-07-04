package application.services;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import application.entities.Commenti;
import application.exceptions.NotFoundException;
import application.payloads.CommentoPayload;
import application.repository.CommentiRepository;

@Service
public class CommentiService {
	@Autowired
	CommentiRepository cr;

	@Autowired
	UtenteService utenteService;
	@Autowired
	PostService postService;

//CRUD commenti
	public Commenti findById(String id) {
		return cr.findById(UUID.fromString(id))
				.orElseThrow(() -> new NotFoundException("Commento con id: " + id + " non trovato!"));
	}

	public Page<Commenti> findAll(int page, String ordinamento) {
		Pageable pagina = PageRequest.of(page, 5, Sort.by(ordinamento));
		return cr.findAll(pagina);
	}

	public Commenti create(CommentoPayload body) {

		Commenti commento = new Commenti();
		commento.setAutore(utenteService.findById(body.getUtenteId()));
		commento.setPostCommentato(postService.findById(body.getPostId()));
		commento.setTesto(body.getTesto());

		return cr.save(commento);
	}

	public Commenti findByIdAndUpdate(String id, CommentoPayload body) {
		Commenti commentoDaModificare = this.findById(id);
		commentoDaModificare.setTesto(body.getTesto());
		commentoDaModificare.setDataUltimoAggiornamento(LocalDate.now());

		return cr.save(commentoDaModificare);
	}

	public void findByIdAndDelete(String id) {

		Commenti commentoDaEliminare = this.findById(id);
		cr.delete(commentoDaEliminare);
	}
//metodi custom

	public Page<Commenti> findByIdPost(String id, int page, String order) {
		Pageable pagina = PageRequest.of(page, 5, Sort.by(order));
		return cr.findByPostCommentato(pagina, postService.findById(id));
	}
}
