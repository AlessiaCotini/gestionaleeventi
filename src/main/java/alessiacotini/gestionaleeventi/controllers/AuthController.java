package alessiacotini.gestionaleeventi.controllers;

import alessiacotini.gestionaleeventi.entities.User;
import alessiacotini.gestionaleeventi.payloads.LoginDTO;
import alessiacotini.gestionaleeventi.payloads.LoginResponseDTO;
import alessiacotini.gestionaleeventi.payloads.UserDTO;
import alessiacotini.gestionaleeventi.services.AuthService;
import alessiacotini.gestionaleeventi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    // http://localhost:3001/auth/register - registrazione - nessuna autorizzazione ma body
//     {
//     "username": "Hermione Granger",
//     "email": "hermione@granger.com",
//     "password": "465ab8a8-b840-4876-9443-ca8f6bbdcdfe",
//     "role": "ORGANIZER"
//                }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody UserDTO body) {
        return this.authService.register(body);
    }

    // http://localhost:3001/auth/login - login - nessuna autorizzazione ma body
//     {
//      "email": "hermione@granger.com",
//      "password": "465ab8a8-b840-4876-9443-ca8f6bbdcdfe"
//                }
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO login){
        return new LoginResponseDTO(this.authService.checkCredentialAndGenerateToken(login));
    }



}
