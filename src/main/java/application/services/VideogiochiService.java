package application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import application.entities.Videogiochi;
import application.entities.enums.Piattaforme;
import application.exceptions.BadRequestException;
import application.exceptions.NotFoundException;
import application.payloads.VideogiochiPayload;
import application.repository.VideogiochiRepository;

@Service
public class VideogiochiService {
	@Autowired
	VideogiochiRepository vr;

	@Autowired
	UtenteService userService;

	private List<Piattaforme> ottieniPiattaformeList(List<String> stringhe) {
		List<Piattaforme> piattaforme = new ArrayList<>();
		for (String str : stringhe) {
			try {
				Piattaforme piattaforma = Piattaforme.valueOf(str.toUpperCase());
				piattaforme.add(piattaforma);
			} catch (IllegalArgumentException e) {
				throw new BadRequestException("piattaforma " + str + " non valida!");
			}
		}
		return piattaforme;
	}

	// CRUD videogiochi

	public Videogiochi findById(String id) {
		return vr.findById(UUID.fromString(id))
				.orElseThrow(() -> new NotFoundException("Videogioco con id: " + id + " non trovato!"));
	}

	public Page<Videogiochi> findAll(int page, String ordinamento) {
		Pageable pagina = PageRequest.of(page, 5, Sort.by(ordinamento));
		return vr.findAll(pagina);
	}

	public Videogiochi create(VideogiochiPayload body) {

		Videogiochi videogiocoCreato = new Videogiochi(body.getNome(), body.getCopertina(), body.getDescrizione(),
				body.getGeneri(), ottieniPiattaformeList(body.getPiattaforme()), body.getAziendaProprietaria(),
				userService.findById(body.getResponsabileId()), body.getVideoTrailer(), body.getDataRilascio());
		return vr.save(videogiocoCreato);
	}

	public Videogiochi findByIdAndUpdate(String id, VideogiochiPayload body) {
		Videogiochi videogiocoModificato = this.findById(id);
		videogiocoModificato.setCopertina(body.getCopertina());
		videogiocoModificato.setAziendaProprietaria(body.getAziendaProprietaria());
		videogiocoModificato.setDataRilascio(body.getDataRilascio());
		videogiocoModificato.setDescrizione(body.getDescrizione());
		videogiocoModificato.setGeneri(body.getGeneri());
		videogiocoModificato.setNome(body.getNome());
		videogiocoModificato.setVideoTrailer(body.getVideoTrailer());
		videogiocoModificato.setPiattaforme(ottieniPiattaformeList(body.getPiattaforme()));

		return vr.save(videogiocoModificato);
	}

	public void findByIdAndDelete(String id) {
		Videogiochi videogiocoEliminato = this.findById(id);

		vr.delete(videogiocoEliminato);
	}

	// metodi custom

}
