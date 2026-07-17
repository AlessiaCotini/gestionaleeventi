package alessiacotini.gestionaleeventi.controllers;

import alessiacotini.gestionaleeventi.entities.Event;
import alessiacotini.gestionaleeventi.entities.User;
import alessiacotini.gestionaleeventi.payloads.EventDTO;
import alessiacotini.gestionaleeventi.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // http://localhost:3001/events -  lista tutti gli eventi
    @GetMapping
    public List<Event> getAllEvents() {
        return this.eventService.findAll();
    }

    // http://localhost:3001/events/{eventId} - dettaglio singolo evento
    @GetMapping("/{id}")
    public Event getEventById(@PathVariable UUID id) {
        return this.eventService.findById(id);
    }

    // http://localhost:3001/events - creazione evento - token + body
//    {
//     "title": "Bob Dylan",
//     "description": "Un incredibile concerto dal vivo",
//     "date": "2026-09-15",
//     "location": "Auditorium parco della musica, Roma",
//     "max_people": 4900
//             }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody EventDTO body, @AuthenticationPrincipal User currentUser) {
        return this.eventService.save(body, currentUser.getUserId());
    }

    // http://localhost:3001/events/{eventId} - modifica evento - solo org - body
    //    {
//     "title": "Bob Dylan in Italia",
//     "description": "Un incredibile concerto dal vivo",
//     "date": "2026-09-18",
//     "location": "Auditorium parco della musica, Roma",
//     "max_people": 4900
//             }
    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable UUID id, @RequestBody EventDTO body, @AuthenticationPrincipal User currentUser) {
        return this.eventService.findByIdAndUpdate(id, body, currentUser.getUserId());
    }

    // http://localhost:3001/events/{eventId} - eliminazione evento - solo org
    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable UUID id, @AuthenticationPrincipal User currentUser) {
        this.eventService.findByIdAndDelete(id, currentUser.getUserId());
    }
}
