package application.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

import application.entities.Gruppo;
import application.payloads.GruppoPayload;
import application.services.GruppoService;

@RestController
@RequestMapping("/gruppo")
public class GruppoController {
	@Autowired
	GruppoService gruppoService;

	// crud per recensione
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void findByIdAndDelete(@PathVariable String id) {
		gruppoService.findByIdAndDelete(id);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Gruppo findByid(@PathVariable String id) {
		return gruppoService.findById(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Gruppo findByidAndUpdate(@PathVariable String id, @RequestBody GruppoPayload payload) {
		return gruppoService.findByIdAndUpdate(id, payload);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.OK)
	public Gruppo create(@RequestBody GruppoPayload payload) {
		return gruppoService.create(payload);
	}

	// endpoint custom
	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public Page<Gruppo> findAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "id") String order, @RequestParam(required = false) String idFondatore,
			@RequestParam(required = false) String nome, @RequestParam(required = false) String argomento,
			@RequestParam(required = false) LocalDate da, @RequestParam(required = false) LocalDate a) {
		if (idFondatore != null) {
			return gruppoService.findLByFondatore(page, order, idFondatore);
		} else if (nome != null) {

			return gruppoService.findByNome(page, order, nome);
		} else if (argomento != null) {

			return gruppoService.findByArgomenti(page, order, argomento);
		} else if (da != null & a != null) {
			return gruppoService.findBydataCreazione(page, order, da, a);
		} else if (da != null) {
			return gruppoService.findBydataCreazione(page, order, da);
		} else {
			return gruppoService.findAll(page, order);
		}
	}
}
