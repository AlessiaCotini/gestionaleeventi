package alessiacotini.gestionaleeventi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "eventi")
public class Event {

    @Id
    @GeneratedValue
    private UUID eventId;

    @Column(name = "nome evento", nullable = false)
    private String title;

    @Column(name = "descrizione evento", nullable = false)
    private String description;

    @Column(name = "data evento", nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String location;

    @Column(name = "posti disponibili", nullable = false)
    private Integer max_people;

    @ManyToOne
    @JoinColumn(name = "userId" , nullable = false)
    private User organizer;

    @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Booking> bookings;

    public Event(String title, String description, LocalDate date, String location, Integer max_people, User organizer) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.max_people = max_people;
        this.organizer = organizer;
    }
}
