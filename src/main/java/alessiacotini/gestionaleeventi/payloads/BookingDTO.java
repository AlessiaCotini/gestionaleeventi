package alessiacotini.gestionaleeventi.payloads;

import alessiacotini.gestionaleeventi.entities.Event;
import alessiacotini.gestionaleeventi.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BookingDTO(
                         @NotNull(message = "Inserire ID utente")
                         UUID userId,

                         @NotNull(message = "Inserire ID evento")
                         UUID eventId) {
}
