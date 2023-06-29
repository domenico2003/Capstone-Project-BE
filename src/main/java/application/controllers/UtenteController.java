package application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.payloads.UtenteDettaglio;
import application.services.UtenteService;

@RestController
@RequestMapping("/utente")
public class UtenteController {

	@Autowired
	private UtenteService us;

	// crud per utente(senza create poichè sta in /auth)
	@DeleteMapping("/{id}")
	public void findByIdAndDelete(@PathVariable String id) {
		us.findByIdAndDelete(id);
	}

	@GetMapping("/{id}")
	public UtenteDettaglio findByid(@PathVariable String id) {
		return us.findByIdDetails(id);
	}
}
