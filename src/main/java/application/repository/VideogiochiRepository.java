package application.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Videogiochi;

public interface VideogiochiRepository extends JpaRepository<Videogiochi, UUID> {

}
