package alessiacotini.gestionaleeventi.services;

import alessiacotini.gestionaleeventi.entities.User;
import alessiacotini.gestionaleeventi.exceptions.AccessDenied;
import alessiacotini.gestionaleeventi.payloads.LoginDTO;
import alessiacotini.gestionaleeventi.payloads.UserDTO;
import alessiacotini.gestionaleeventi.security.JWTTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final JWTTools jwtTools;
    private final PasswordEncoder bcrypt;

    public AuthService(UserService userService, JWTTools jwtTools, PasswordEncoder bcrypt) {
        this.userService = userService;
        this.jwtTools = jwtTools;
        this.bcrypt = bcrypt;
    }

    public User register(UserDTO body) {
        return this.userService.save(body);
    }

    public String checkCredentialAndGenerateToken(LoginDTO body) {
        User trovato = this.userService.findByEmail(body.email());

        if (this.bcrypt.matches(body.password(), trovato.getPassword())) {
            return this.jwtTools.generoToken(trovato);
        } else {
            throw new AccessDenied("Credenziali errate");
        }
    }
}
