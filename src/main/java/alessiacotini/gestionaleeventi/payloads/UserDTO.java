package alessiacotini.gestionaleeventi.payloads;

import alessiacotini.gestionaleeventi.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotBlank
        String username,

        @NotBlank(message = "Email necessaria")
        @Email
        String email,

        @NotBlank(message = "Password necessaria")
        @Size(min = 5)
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "La password deve contenere i caratteri necessari")
        String password,

        @NotBlank
        Role role
) {
}

