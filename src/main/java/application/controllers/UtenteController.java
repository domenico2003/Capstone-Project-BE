package application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import application.entities.Utente;
import application.payloads.ResponsePayload;
import application.payloads.UpdateUtentePayload;
import application.payloads.UtenteDettaglio;
import application.services.UtenteService;

@RestController
@RequestMapping("/utente")
public class UtenteController {

	@Autowired
	UtenteService utenteService;

// crud per utente(senza create poichè sta in /auth)
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void findByIdAndDelete(@PathVariable String id) {
		utenteService.findByIdAndDelete(id);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UtenteDettaglio findByid(@PathVariable String id) {
		return utenteService.findByIdDetails(id);
	}

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public Page<Utente> findAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "id") String order) {
		return utenteService.findAll(page, order);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Utente findByidAndUpdate(@PathVariable String id, @RequestBody UpdateUtentePayload payload) {
		return utenteService.findByIdAndUpadate(id, payload);
	}

//endpoint custom

	@PostMapping("/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ResponsePayload> uniscitiAGruppo(@PathVariable String userId,
			@RequestParam(required = false) String gruppoId) {

		if (gruppoId != null) {

			utenteService.uniscitiAGruppo(userId, gruppoId);
			return new ResponseEntity<ResponsePayload>(new ResponsePayload("congratulazioni, ti sei unito al gruppo"),
					HttpStatus.OK);

		} else {
			utenteService.abbandonaGruppo(userId);
			return new ResponseEntity<ResponsePayload>(new ResponsePayload("gruppo abbandonato con successo!"),
					HttpStatus.OK);
		}
	}
}
