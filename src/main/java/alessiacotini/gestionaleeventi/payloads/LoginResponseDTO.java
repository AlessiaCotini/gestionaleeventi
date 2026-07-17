package alessiacotini.gestionaleeventi.payloads;

import jakarta.validation.constraints.NotBlank;

public record LoginResponseDTO(@NotBlank String accessToken) {
}