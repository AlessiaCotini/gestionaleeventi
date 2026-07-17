package alessiacotini.gestionaleeventi.repositories;

import alessiacotini.gestionaleeventi.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    // numero prenotazioni per evento
    int countByEventId(UUID eventId);

    // prenotazioni per utente
    List<Booking> findByUserId(UUID userId);

    // no duplicati utente prenotazione
    boolean existsByUserIdAndEventId(UUID userId, UUID eventId);
}
