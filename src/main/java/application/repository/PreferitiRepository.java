package application.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Preferiti;

public interface PreferitiRepository extends JpaRepository<Preferiti, UUID> {

}
