package application.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import application.entities.Genere;
import application.entities.Utente;
import application.entities.Videogiochi;
import application.entities.enums.Piattaforme;
import application.exceptions.BadRequestException;
import application.exceptions.NotFoundException;
import application.payloads.VideogiochiPayload;
import application.repository.GeneriRepository;
import application.repository.VideogiochiRepository;

@Service
public class VideogiochiService {
	@Autowired
	VideogiochiRepository vr;
	@Autowired
	GeneriRepository gr;
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

	public Videogiochi create(VideogiochiPayload body) {

		Videogiochi videogiocoCreato = new Videogiochi(body.getNome(), body.getCopertina(), body.getDescrizione(),
				ottieniPiattaformeList(body.getPiattaforme()), body.getAziendaProprietaria(),
				userService.findById(body.getResponsabileId()), body.getVideoTrailer(), body.getDataRilascio());

		for (String genere : body.getGeneri()) {
			Genere gen = gr.findByNomeIgnoreCase(genere);

			if (gen == null) {
				Genere genereSalvato = new Genere(genere);
				gr.save(genereSalvato);

				Genere genereRicavato = gr.findByNomeIgnoreCase(genere);
				videogiocoCreato.addGeneri(genereRicavato);
			} else {
				videogiocoCreato.addGeneri(gen);
			}
		}
		return vr.save(videogiocoCreato);
	}

	public Videogiochi findByIdAndUpdate(String id, VideogiochiPayload body) {
		Videogiochi videogiocoModificato = this.findById(id);
		videogiocoModificato.setCopertina(body.getCopertina());
		videogiocoModificato.setAziendaProprietaria(body.getAziendaProprietaria());
		videogiocoModificato.setDataRilascio(body.getDataRilascio());
		videogiocoModificato.setDescrizione(body.getDescrizione());
		videogiocoModificato.setGeneri(new ArrayList<>());
		for (String genere : body.getGeneri()) {
			Genere gen = gr.findByNomeIgnoreCase(genere);

			if (gen == null) {
				Genere genereSalvato = new Genere(genere);
				gr.save(genereSalvato);

				Genere genereRicavato = gr.findByNomeIgnoreCase(genere);
				videogiocoModificato.addGeneri(genereRicavato);
			} else {
				videogiocoModificato.addGeneri(gen);
			}
		}
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
	public Page<Videogiochi> findAll(int page, String ordinamento) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));
		return vr.findAll(pagina);
	}

	public Page<Videogiochi> findByResponsabile(int page, String ordinamento, String responsabileEmail) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));
		Utente resp = userService.findByEmail(responsabileEmail);
		return vr.findByResponsabile(pagina, resp);
	};

	public Page<Videogiochi> findByNome(int page, String ordinamento, String nome) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));
		return vr.findByNomeStartingWithIgnoreCase(pagina, nome);
	};

	public Page<Videogiochi> findByGeneri(int page, String ordinamento, String genereNome) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));
		List<Genere> gen = gr.findByNomeStartingWithIgnoreCase(genereNome);

		if (gen.size() == 1) {
			return vr.findByGeneri(pagina, gen.get(0));
		} else if (gen.size() > 1) {
			throw new BadRequestException("ricerca un genere più preciso!");
		} else {
			throw new NotFoundException("genere non trovato");
		}
	};

	public Page<Videogiochi> findByAziendaProprietaria(int page, String ordinamento, String aziendaProprietaria) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));
		return vr.findByAziendaProprietariaStartingWithIgnoreCase(pagina, aziendaProprietaria);
	};

	public Page<Videogiochi> findByValutazioneMedia(int page, String ordinamento, int valutazioneMedia) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));
		return vr.findByValutazioneMedia(pagina, valutazioneMedia);
	};

	public Page<Videogiochi> findByDataRilascio(int page, String ordinamento, LocalDate da, LocalDate a) {
		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));
		return vr.findByDataRilascioBetween(pagina, da, a);
	};

	public Page<Videogiochi> findByDataRilascio(int page, String ordinamento, LocalDate dataRilascio) {

		Pageable pagina = PageRequest.of(page, 10, Sort.by(ordinamento));
		return vr.findByDataRilascio(pagina, dataRilascio);
	};
}
