package application.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	JwtFilter jwtAuthFilter;

	@Autowired
	CorsFilter corsFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.csrf(c -> c.disable());

		http.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll());

		http.authorizeHttpRequests(auth -> auth.requestMatchers("/utente/**").authenticated());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/commento/**").authenticated());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/gruppo/**").authenticated());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/post/**").authenticated());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/preferiti/**").authenticated());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/recensione/**").authenticated());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/admin/**").hasAuthority("ADMIN"));

		http.authorizeHttpRequests(auth -> auth.requestMatchers("/videogioco/**").authenticated()
				.requestMatchers(HttpMethod.POST, "/**").hasAnyAuthority("GAME_CREATOR", "ADMIN")
				.requestMatchers(HttpMethod.PUT, "/**").hasAnyAuthority("GAME_CREATOR", "ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/**").hasAnyAuthority("GAME_CREATOR", "ADMIN"));

		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(corsFilter, JwtFilter.class);

		return http.build();
	}

	@Bean
	PasswordEncoder pwEncoder() {
		return new BCryptPasswordEncoder(10);
	}
}
