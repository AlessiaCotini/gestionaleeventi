package alessiacotini.gestionaleeventi.services;

import alessiacotini.gestionaleeventi.entities.Event;
import alessiacotini.gestionaleeventi.entities.User;
import alessiacotini.gestionaleeventi.exceptions.NotFound;
import alessiacotini.gestionaleeventi.exceptions.Unauthorized;
import alessiacotini.gestionaleeventi.payloads.EventDTO;
import alessiacotini.gestionaleeventi.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;

    public EventService(EventRepository eventRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    public List<Event> findAll() {
        return this.eventRepository.findAll();
    }

    public Event findById(UUID eventId) {
        return this.eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFound("Evento non trovato"));
    }

    public Event save(EventDTO body, UUID organizerId) {
        User organizzatore = this.userService.findById(organizerId);

        if (!organizzatore.getRole().equals("ORGANIZER")) {
            throw new Unauthorized("Solo gli organizzatori possono creare eventi");
        }

        Event nuovoEvento = new Event(
                body.title(),
                body.description(),
                body.date(),
                body.location(),
                body.max_people(),
                organizzatore
        );

        return this.eventRepository.save(nuovoEvento);
    }

    public Event findByIdAndUpdate(UUID eventId, EventDTO body, UUID currentUserId) {
        Event trovato = this.findById(eventId);

        if (!trovato.getOrganizer().getUserId().equals(currentUserId)) {
            throw new Unauthorized("Non sei autorizzato a modificare questo evento perché non lo hai creato tu");
        }

        trovato.setTitle(body.title());
        trovato.setDescription(body.description());
        trovato.setDate(body.date());
        trovato.setLocation(body.location());
        trovato.setMax_people(body.max_people());

        return this.eventRepository.save(trovato);
    }

    public void findByIdAndDelete(UUID eventId, UUID currentUserId) {
        Event trovato = this.findById(eventId);

        if (!trovato.getOrganizer().getUserId().equals(currentUserId)) {
            throw new Unauthorized("Non sei autorizzato a eliminare questo evento");
        }

        this.eventRepository.delete(trovato);
    }
}
