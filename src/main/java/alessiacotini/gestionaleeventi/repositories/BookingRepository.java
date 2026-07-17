package alessiacotini.gestionaleeventi.repositories;

import alessiacotini.gestionaleeventi.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    // prenotazioni per un evento
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.event.id = :eventId")
    long countByEventId(@Param("eventId") UUID eventId);

    // prenotazioni di uno specifico utente
    @Query(value = "SELECT * FROM bookings WHERE user_id = :userId", nativeQuery = true)
    List<Booking> findByUserId(@Param("userId") UUID userId);

    // se esiste già una prenotazione per utente-evento
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM bookings WHERE user_id = :userId AND event_id = :eventId", nativeQuery = true)
    boolean existsByUserIdAndEventId(@Param("userId") UUID userId, @Param("eventId") UUID eventId);
}
