package application.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import application.entities.Genere;
import application.entities.Videogiochi;
import application.payloads.VideogiochiPayload;
import application.services.VideogiochiService;

@RestController
@RequestMapping("/videogioco")
public class VideogiochiController {
	@Autowired
	VideogiochiService videogiochiService;

	// crud per videogioco

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('GAME_CREATOR','ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public void findByIdAndDelete(@PathVariable String id) {
		videogiochiService.findByIdAndDelete(id);
	}

	@GetMapping("/all/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Videogiochi findByid(@PathVariable String id) {

		return videogiochiService.findById(id);
	}

	@GetMapping("/generi")
	@ResponseStatus(HttpStatus.OK)
	public List<Genere> findByid() {
		return videogiochiService.findAllGeneri();
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('GAME_CREATOR','ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public Videogiochi findByidAndUpdate(@PathVariable String id, @RequestBody VideogiochiPayload payload) {
		return videogiochiService.findByIdAndUpdate(id, payload);
	}

	@PostMapping("")
	@PreAuthorize("hasAnyAuthority('GAME_CREATOR','ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public Videogiochi create(@RequestBody VideogiochiPayload payload) {
		return videogiochiService.create(payload);
	}
	// endpoint custom

	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public Page<Videogiochi> findAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String order,
			@RequestParam(required = false) String responsabileEmail, @RequestParam(required = false) String nome,
			@RequestParam(required = false) String genere, @RequestParam(required = false) String aziendaProprietaria,
			@RequestParam(defaultValue = "-1") int valutazioneMedia,
			@RequestParam(required = false) LocalDate dataRilacioDa,
			@RequestParam(required = false) LocalDate dataRilacioA) {

		if (responsabileEmail != null) {

			return videogiochiService.findByResponsabile(page, order, size, responsabileEmail);
		} else if (nome != null) {
			return videogiochiService.findByNome(page, order, size, nome);
		} else if (genere != null) {
			return videogiochiService.findByGeneri(page, order, size, genere);
		} else if (aziendaProprietaria != null) {
			return videogiochiService.findByAziendaProprietaria(page, order, size, aziendaProprietaria);
		} else if (valutazioneMedia != -1) {
			return videogiochiService.findByValutazioneMedia(page, order, size, valutazioneMedia);
		} else if (dataRilacioDa != null & dataRilacioA != null) {
			return videogiochiService.findByDataRilascio(page, order, size, dataRilacioDa, dataRilacioA);
		} else if (dataRilacioDa != null) {
			return videogiochiService.findByDataRilascio(page, order, size, dataRilacioDa);
		} else {
			return videogiochiService.findAll(page, order, size);
		}
	}
}
