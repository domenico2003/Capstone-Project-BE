package application.controllers;

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

import application.entities.Recensione;
import application.payloads.RecensionePayload;
import application.services.RecensioneService;

@RestController
@RequestMapping("/recensione")
public class RecensioneController {
	@Autowired
	RecensioneService recensioneService;

	// crud per recensione
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void findByIdAndDelete(@PathVariable String id) {
		recensioneService.findByIdAndDelete(id);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Recensione findByid(@PathVariable String id) {
		return recensioneService.findById(id);
	}

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public Page<Recensione> findAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "id") String order) {
		return recensioneService.findAll(page, order);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Recensione findByidAndUpdate(@PathVariable String id, @RequestBody RecensionePayload payload) {
		return recensioneService.findByIdAndUpdate(id, payload);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.OK)
	public Recensione create(@RequestBody RecensionePayload payload) {
		return recensioneService.create(payload);
	}
	// endpoint custom
}
