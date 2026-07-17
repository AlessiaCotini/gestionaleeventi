package alessiacotini.gestionaleeventi.controllers;

import alessiacotini.gestionaleeventi.entities.Booking;
import alessiacotini.gestionaleeventi.entities.User;
import alessiacotini.gestionaleeventi.exceptions.BadRequest;
import alessiacotini.gestionaleeventi.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // http://localhost:3001/bookings -  crea una prenotazione - user
    // "eventId": " "
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(@RequestBody Map<String, UUID> payload, @AuthenticationPrincipal User currentUser) {
        UUID eventId = payload.get("eventId");
        if (eventId == null) {
            throw new BadRequest("Il campo eventId è obbligatorio");
        }
        return this.bookingService.save(currentUser.getUserId(), eventId);
    }

    // http://localhost:3001/bookings/me -  ottiene tutte le prenotazioni dell'utente che ha effettuato il login
    @GetMapping("/me")
    public List<Booking> getMyBookings(@AuthenticationPrincipal User currentUser) {
        return this.bookingService.findByUserId(currentUser.getUserId());
    }

    // http://localhost:3001/bookings/{bookingId} -  annulla una prenotazione
    @DeleteMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelBooking(@PathVariable UUID bookingId, @AuthenticationPrincipal User currentUser) {
        this.bookingService.findByIdAndDelete(bookingId, currentUser.getUserId());
    }
}
