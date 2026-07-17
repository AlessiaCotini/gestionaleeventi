package alessiacotini.gestionaleeventi.payloads;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record EventDTO(
        @NotBlank(message = "Inserire titolo")
        String title,

        @NotBlank(message = "Inserire descrizione")
        String description,

        @NotNull
        LocalDate date,

        @NotBlank(message = "Luogo necessario")
        String location,

        @NotNull(message = "Inserire numero massimo di posti")
        @Min(value = 1, message = "Ci deve essere almeno 1 posto disponibile")
        Integer max_people,

        @NotNull
        UUID userID
) {
}
