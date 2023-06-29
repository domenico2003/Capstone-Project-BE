package application.services;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import application.entities.Post;
import application.exceptions.NotFoundException;
import application.payloads.PostPayload;
import application.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	PostRepository pr;

	@Autowired
	UtenteService userService;

	@Autowired
	GruppoService gruppoService;

// CRUD per post

	public Post findById(String id) {
		return pr.findById(UUID.fromString(id))
				.orElseThrow(() -> new NotFoundException("post con id: " + id + " non trovato!"));
	}

	public Page<Post> findAll(int page, String ordinamento) {
		Pageable pagina = PageRequest.of(page, 5, Sort.by(ordinamento));
		return pr.findAll(pagina);
	}

	public Post create(PostPayload body) {
		Post postCreato = new Post(body.getContenuto(), gruppoService.findById(body.getGruppoId()),
				userService.findById(body.getUtenteCheLoHaPublicatoId()), body.getImmagine());
		return pr.save(postCreato);
	}

	public Post findByIdAndUpdate(String id, PostPayload body) {
		Post postModificato = this.findById(id);
		postModificato.setContenuto(body.getContenuto());
		postModificato.setImmagine(body.getImmagine());
		postModificato.setDataUltimoAggiornamento(LocalDate.now());
		return pr.save(postModificato);
	}

	public void findByIdAndDelete(String id) {
		Post postEliminato = this.findById(id);

		pr.delete(postEliminato);
	}

//metodi custom
}