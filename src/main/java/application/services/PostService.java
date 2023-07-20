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

	public Page<Post> findAll(int page) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by("dataCreazione").descending());
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

	// Find per gruppo
	public Page<Post> findByGruppo(String idGruppo, int page) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by("dataCreazione").descending());
		return pr.findByGruppo(pagina, gruppoService.findById(idGruppo));
	}

	// Find per utente
	public Page<Post> findByUtente(String idUtente, int page) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by("dataCreazione").descending());
		return pr.findByUtenteCheLoHaPublicato(pagina, userService.findById(idUtente));
	}

	public void addMiPiace(String idPost) {
		Post post = this.findById(idPost);
		post.aggiungiMiPiace();
		pr.save(post);
	}

}
