package application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import application.exceptions.BadRequestException;
import application.payloads.ResponsePayload;
import application.services.GruppoService;
import application.services.UtenteService;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UtenteService userservice;

	@Autowired
	GruppoService gruppoService;

	@PostMapping("/setRuoli")
	public ResponseEntity<ResponsePayload> setRuoli(@RequestParam(defaultValue = "false") String gameCreator,
			@RequestParam(defaultValue = "false") String admin, @RequestParam String emailUtente) {
		if (gameCreator.equalsIgnoreCase("true")) {

			userservice.aggiungiGameCreator(emailUtente);
			return new ResponseEntity<ResponsePayload>(new ResponsePayload("operazione completata!"), HttpStatus.OK);
		} else if (admin.equalsIgnoreCase("true")) {
			userservice.aggiungiAdmin(emailUtente);
			return new ResponseEntity<ResponsePayload>(new ResponsePayload("operazione completata!"), HttpStatus.OK);
		} else {
			throw new BadRequestException("Parametro necessario!");
		}
	}

	@PostMapping("{emailResponsabile}")
	public ResponseEntity<ResponsePayload> updateById(@PathVariable String emailResponsabile,
			@RequestParam(required = false) String giocoId, @RequestParam(required = false) String gruppoId) {
		if (giocoId != null) {
			userservice.aggiungiResponsabileAVideogioco(giocoId, emailResponsabile);
			return new ResponseEntity<ResponsePayload>(new ResponsePayload("responsabile Videogioco nominato!"),
					HttpStatus.OK);
		} else if (gruppoId != null) {
			gruppoService.setNuovoFondatore(emailResponsabile, gruppoId);
			return new ResponseEntity<ResponsePayload>(new ResponsePayload("responsabile Gruppo nominato!"),
					HttpStatus.OK);
		} else {
			throw new BadRequestException("inserire un solo parametro!");
		}
	}

}
