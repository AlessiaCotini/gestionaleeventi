package alessiacotini.gestionaleeventi.errors;


import alessiacotini.gestionaleeventi.exceptions.AccessDenied;
import alessiacotini.gestionaleeventi.exceptions.BadRequest;
import alessiacotini.gestionaleeventi.exceptions.Conflict;
import alessiacotini.gestionaleeventi.exceptions.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GestioneErrori {
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Errore badRequest(BadRequest ex){
        return new Errore(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Errore notFound(NotFound ex){
        return new Errore(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Conflict.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Errore conflict(Conflict ex){
        return new Errore(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Errore unauthorized(BadRequest ex){
        return new Errore(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Errore accessDenied(AccessDenied ex){
        return new Errore(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Errore errGenerale(Exception ex){
        ex.printStackTrace();
        return new Errore("Errore default", LocalDateTime.now());
    }


}
