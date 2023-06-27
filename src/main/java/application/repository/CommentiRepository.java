package application.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Commenti;

public interface CommentiRepository extends JpaRepository<Commenti, UUID> {

}
