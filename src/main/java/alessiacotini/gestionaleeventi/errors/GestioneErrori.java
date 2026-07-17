package alessiacotini.gestionaleeventi.errors;


import alessiacotini.gestionaleeventi.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GestioneErrori {

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> badRequest(HttpClientErrorException.BadRequest ex) {
        return creaRispostaErrore(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage());
    }


    @ExceptionHandler(NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> notFound(NotFound ex) {
        return creaRispostaErrore(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage());
    }

    @ExceptionHandler(Conflict.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> conflict(Conflict ex) {
        return creaRispostaErrore(HttpStatus.CONFLICT, "Conflict", ex.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> unauthorized(HttpClientErrorException.Unauthorized ex) {
        return creaRispostaErrore(HttpStatus.UNAUTHORIZED, "Unauthorized", "Autenticazione fallita o token mancante.");
    }

    @ExceptionHandler(AccessDenied.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> accessDenied(AccessDenied ex) {
        return creaRispostaErrore(HttpStatus.FORBIDDEN, "Forbidden", "Non hai i permessi necessari per accedere a questa risorsa.");
    }

    @ExceptionHandler(OverBooking.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleOverbooking(OverBooking ex) {
        return creaRispostaErrore(HttpStatus.BAD_REQUEST, "Overbooking", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> errGenerale(Exception ex) {
        ex.printStackTrace();
        return creaRispostaErrore(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "Si è verificato un errore imprevisto nel server.");
    }

    private Map<String, Object> creaRispostaErrore(HttpStatus status, String errore, String messaggio) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", errore);
        response.put("message", messaggio);
        return response;
    }
}
