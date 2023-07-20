package application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping("/login")
	public ResponseEntity<TokenPayload> login(@RequestBody LoginPayload body) {

		Utente u = utenteservice.findByEmail(body.getEmail());

		String plainPW = body.getPassword();

		String hashedPW = u.getPassword();

		if (!bcrypt.matches(plainPW, hashedPW))
			throw new UnauthorizedException("Credenziali non valide");

		String token = JwtTools.createToken(u);

		return new ResponseEntity<>(new TokenPayload(token), HttpStatus.OK);
	}

	@PostMapping("/register")
	public Utente register(@RequestBody CreateUtentePayload payload) {
		payload.setPassword(bcrypt.encode(payload.getPassword()));
		return utenteservice.create(payload);
	}

}
