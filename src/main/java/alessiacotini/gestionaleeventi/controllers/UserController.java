package alessiacotini.gestionaleeventi.controllers;

import alessiacotini.gestionaleeventi.entities.User;
import alessiacotini.gestionaleeventi.payloads.UserDTO;
import alessiacotini.gestionaleeventi.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utenti")
public class UserController {
    private final UserService userService;

    public UserController(UserService usersService) {
        this.userService = usersService;
    }


    // http://localhost:3001/utenti - crea utente - auth organizer - + body
//     {
//     "name": "Harry Potter",
//     "email": "harry@potter.com",
//     "password": "465ab8a8-b840-4876-9443-ca8f6bbdcdfe",
//     "role": "USER"
//     }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ORGANIZER')")
    public User createUtente (@RequestBody UserDTO utente){
        return  this.userService.save(utente);
    }


    // http://localhost:3001/utenti - lista utenti - token
    @GetMapping
    public List<User> getAll(){
        return this.userService.findAll();
    }


    // http://localhost:3001/utenti/me - visualizza mio profilo - token
    @GetMapping("/me")
    public User getMyProfile (@AuthenticationPrincipal User myProfile){
        return myProfile;
    }
}
