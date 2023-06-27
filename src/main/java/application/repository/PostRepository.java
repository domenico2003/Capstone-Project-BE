package application.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Post;

public interface PostRepository extends JpaRepository<Post, UUID> {

}
