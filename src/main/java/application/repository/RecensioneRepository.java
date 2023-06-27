package application.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Recensione;

public interface RecensioneRepository extends JpaRepository<Recensione, UUID> {

}
