package application.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Gruppo;

public interface GruppoRepository extends JpaRepository<Gruppo, UUID> {

}
