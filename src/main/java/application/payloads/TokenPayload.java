package application.payloads;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenPayload {
	private String JwtToken;
	private UUID id;
}
