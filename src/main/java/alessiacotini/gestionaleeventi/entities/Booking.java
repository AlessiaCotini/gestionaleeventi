package alessiacotini.gestionaleeventi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "prenotazioni")
public class Booking {

    @Id
    @GeneratedValue
    private UUID bookingId;

    @Column(nullable = false)
    LocalDate date;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "eventId", nullable = false)
    private Event event;

    public Booking(User user, Event event) {
        this.date = LocalDate.now();
        this.user = user;
        this.event = event;
    }
}
