package alessiacotini.gestionaleeventi.exceptions;

public class OverBooking extends RuntimeException {
    public OverBooking(String message) {
        super(message);
    }
}
