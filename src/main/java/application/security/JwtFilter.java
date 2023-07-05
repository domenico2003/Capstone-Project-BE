package application.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import application.entities.Utente;
import application.exceptions.UnauthorizedException;
import application.services.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	UtenteService utenteService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (!request.getMethod().equalsIgnoreCase("OPTIONS")) {

			String authHeader = request.getHeader("Authorization");

			if (authHeader == null || !authHeader.startsWith("Bearer "))
				throw new UnauthorizedException("Aggiungere il token all'authorization header!");
			String token = authHeader.substring(7);

			JwtTools.isTokenValid(token);

			String id = JwtTools.extractSubject(token);

			Utente utenteCorrente = utenteService.findById(id);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(utenteCorrente,
					null, utenteCorrente.getAuthorities());

			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authToken);

			filterChain.doFilter(request, response);

		} else {
			filterChain.doFilter(request, response);
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		boolean match = new AntPathMatcher().match("/auth/**", request.getServletPath());
		if (match) {
			return new AntPathMatcher().match("/auth/**", request.getServletPath());
		} else {
			return new AntPathMatcher().match("/videogioco/all/**", request.getServletPath());
		}
	}

}
