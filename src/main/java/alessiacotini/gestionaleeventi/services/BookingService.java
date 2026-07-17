package alessiacotini.gestionaleeventi.services;

import alessiacotini.gestionaleeventi.entities.Booking;
import alessiacotini.gestionaleeventi.entities.Event;
import alessiacotini.gestionaleeventi.entities.User;
import alessiacotini.gestionaleeventi.exceptions.BadRequest;
import alessiacotini.gestionaleeventi.exceptions.NotFound;
import alessiacotini.gestionaleeventi.repositories.BookingRepository;
import alessiacotini.gestionaleeventi.repositories.EventRepository;
import alessiacotini.gestionaleeventi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public Booking save(UUID userId, UUID eventId) {
        User trovato = this.userRepository.findById(userId).orElseThrow();
        Event evento = this.eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFound("Evento non trovato"));

        long prenotazioniAttuali = this.bookingRepository.countByEventId(eventId);

        if (prenotazioniAttuali >= evento.getMax_people()) {
            throw new BadRequest("Spiacenti, i posti per questo evento sono esauriti!");
        }
        boolean giaPrenotato = this.bookingRepository.existsByUserIdAndEventId(userId, eventId);
        if (giaPrenotato) {
            throw new BadRequest("Hai già prenotato un posto per questo evento");
        }
        Booking nuovaPrenotazione = new Booking(trovato, evento);
        return this.bookingRepository.save(nuovaPrenotazione);
    }

    public Booking findById(UUID bookingId) {
        return this.bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFound("Prenotazione non trovata"));
    }

    public List<Booking> findAll() {
        return this.bookingRepository.findAll();
    }

    public List<Booking> findByUserId(UUID userId) {
        return this.bookingRepository.findByUserId(userId);
    }

    public void findByIdAndDelete(UUID bookingId, UUID userId){
        User trovato = this.userRepository.findById(userId).orElseThrow();
        Booking prenotazioneEsistente = this.bookingRepository.findById(bookingId).orElseThrow();
        if(trovato.getUserId() == userId && prenotazioneEsistente.getBookingId() == bookingId){
            bookingRepository.delete(prenotazioneEsistente);
        }

    }


}
