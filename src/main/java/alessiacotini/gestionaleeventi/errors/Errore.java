package alessiacotini.gestionaleeventi.errors;

import java.time.LocalDateTime;

public record Errore(String message, LocalDateTime time) {
}
