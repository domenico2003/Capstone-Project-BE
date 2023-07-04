package application.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Gruppo;
import application.entities.Post;
import application.entities.Utente;

public interface PostRepository extends JpaRepository<Post, UUID> {

	Page<Post> findByGruppo(Pageable pagina, Gruppo gruppo);

	Page<Post> findByUtenteCheLoHaPublicato(Pageable pagina, Utente utenteCheLoHaPublicato);

}
