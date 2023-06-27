package application.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import application.ESEMPII.Immagine;
import application.ESEMPII.ImmagineRepository;
import application.entities.Utente;
import application.exceptions.UnauthorizedException;
import application.payloads.CreateUtentePayload;
import application.payloads.LoginPayload;
import application.payloads.TokenPayload;
import application.security.JwtTools;
import application.services.UtenteService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UtenteService utenteservice;

	@Autowired
	private PasswordEncoder bcrypt;

	@Autowired
	private ImmagineRepository immagineRepository;

	@PostMapping("/immagini")
	public String uploadImmagine(@RequestParam("file") MultipartFile file) throws IOException {
		Immagine immagine = new Immagine();
		immagine.setImmagine(file.getBytes());
		immagineRepository.save(immagine);
		return "Immagine caricata con successo";
	}

	@PostMapping("/login")
	public ResponseEntity<TokenPayload> login(@RequestBody LoginPayload body) {

		Utente u = utenteservice.findByEmail(body.getEmail());

		String plainPW = body.getPassword();
		String hashedPW = u.getPassword();

		if (!bcrypt.matches(plainPW, hashedPW))
			throw new UnauthorizedException("Credenziali non valide");

		String token = JwtTools.createToken(u);

		return new ResponseEntity<>(new TokenPayload(token, u.getId()), HttpStatus.OK);
	}

	@PostMapping("/register")
	public Utente register(@RequestBody CreateUtentePayload payload) {
		payload.setPassword(bcrypt.encode(payload.getPassword()));
		return utenteservice.create(payload);
	}
}
