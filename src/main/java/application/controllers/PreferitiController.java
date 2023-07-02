package application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import application.entities.Preferiti;
import application.payloads.PreferitiPayload;
import application.services.PreferitiService;

@RestController
@RequestMapping("/preferiti")
public class PreferitiController {
	@Autowired
	PreferitiService preferitiService;

	// crud per preferiti
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void findByIdAndDelete(@PathVariable String id) {
		preferitiService.findByIdAndDelete(id);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.OK)
	public Preferiti create(@RequestBody PreferitiPayload payload) {
		return preferitiService.create(payload);
	}
	// endpoint custom
}
