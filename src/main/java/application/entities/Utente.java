package application.entities;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import application.entities.enums.UtenteRuoli;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({ "password", "accountNonExpired", "accountNonLocked", "enabled", "credentialsNonExpired",
		"authorities", "videogiochiAggiuntiAlSito", "videogiochiPiaciuti", "postPublicati", "gruppo", "gruppiCreati",
		"recensioniRilasciate", "commenti" })
public class Utente implements UserDetails {

	// colonne della tabella che si genera
	@Id
	@GeneratedValue
	private UUID id;
	private String email;
	private String password;
	@Column(length = 6000)
	private String immagineProfilo = "https://thumbs.dreamstime.com/b/icona-predefinita-di-profilo-dell-avatar-105356015.jpg";
	private String nome;
	private String cognome;
	private String username;
	// gli imposto un valore di default su user poichè tutti gli utenti creati
	// saranno USER
	@Enumerated(EnumType.STRING)
	private UtenteRuoli ruolo = UtenteRuoli.USER;
	private boolean isEnabled;
	private boolean isCredentialsNonExpired;
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;

	// videogiochi
	@OneToMany(mappedBy = "responsabile")
	private List<Videogiochi> videogiochiAggiuntiAlSito;

	@OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Preferiti> videogiochiPiaciuti;

	@OneToMany(mappedBy = "utenteCheLoHaPublicato", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Post> postPublicati;

	// gruppi
	@OneToMany(mappedBy = "fondatore")
	private List<Gruppo> gruppiCreati;

	// recensione
	@OneToMany(mappedBy = "utente")
	private List<Recensione> recensioniRilasciate;

	@ManyToOne //
	private Gruppo gruppo;

	// commenti
	@OneToMany(mappedBy = "autore")
	private List<Commenti> commenti;

	// costruttore
	public Utente(String email, String password, String nome, String cognome, String username) {
		super();
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.isEnabled = true;
		this.isCredentialsNonExpired = true;
		this.isAccountNonExpired = true;
		this.isAccountNonLocked = true;
	}

	// metodi
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(ruolo.name()));
	}

}
