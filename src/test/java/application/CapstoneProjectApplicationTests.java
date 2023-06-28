package application;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class CapstoneProjectApplicationTests {

//	modello test
//	mockMvc.perform(MockMvcRequestBuilders
//            .post("/api/endpoint")
//            .contentType(MediaType.APPLICATION_JSON)
//            .header("Authorization", "Bearer " + token)
//            .content(requestBody)
//            .param("parametro", "valore"))
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.risposta").value("valore_aspettato"));
//}

	@Autowired
	private MockMvc test;
	private String password = "1234";
	private String idUtenteLoggato;
	private String token;

	@BeforeAll
	void registerAndLogin() throws Exception {
//registro un utente per prima cosa
		String body = "{\"username\":\"esempi.username\",\"email\":\"esempioTest@gmail.com\",\"password\": \""
				+ this.password + "\",\"nome\":\"Domenico\",\"cognome\": \"Dattilo\"}";

		test.perform(MockMvcRequestBuilders.post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(body)
				.servletPath("/auth/register")).andExpect(MockMvcResultMatchers.status().isOk());
//ed effettuo il login con l'utente creato sopra
		String bodyLogin = "{\"email\":\"esempioTest@gmail.com\",\"password\": \"1234\"}";
		String rispostaLogin = test
				.perform(MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON)
						.content(bodyLogin).servletPath("/auth/login"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();

		this.token = rispostaLogin.split(",")[1].split(":")[1].split("\"")[1];
		this.idUtenteLoggato = rispostaLogin.split(",")[0].split(":")[1].split("\"")[1];
	}

	@Test
	void test1() {
		System.out.println(idUtenteLoggato);
		System.out.println(token);

	}

	@AfterAll
	void deleteUser() throws Exception {

		test.perform(MockMvcRequestBuilders.delete("/utente/" + idUtenteLoggato)
				.header("Authorization", "Bearer " + token).servletPath("/utente/" + idUtenteLoggato))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	// funzione per stampare in console
	private void stamp(String string) {
		System.out.println(string);
	}

}
