package alessiacotini.gestionaleeventi.services;

import alessiacotini.gestionaleeventi.entities.User;
import alessiacotini.gestionaleeventi.exceptions.AccessDenied;
import alessiacotini.gestionaleeventi.exceptions.Conflict;
import alessiacotini.gestionaleeventi.exceptions.NotFound;
import alessiacotini.gestionaleeventi.payloads.UserDTO;
import alessiacotini.gestionaleeventi.repositories.UserRepository;
import org.springframework.boot.context.config.ConfigDataException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder bcrypt;

    public UserService(UserRepository userRepository, PasswordEncoder bcrypt) {
        this.userRepository = userRepository;
        this.bcrypt = bcrypt;
    }


    public User save(UserDTO body) {
        if (this.userRepository.existsByEmail(body.email())) {
            throw new AccessDenied("Email già in utilizzo");
        }
        User nuovo = new User(
                body.username(),
                body.email(),
                this.bcrypt.encode(body.password()),
                body.role());
        return userRepository.save(nuovo);
    }

    public User findById(UUID userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFound("Utente non trovato"));
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFound("Email non trovata"));
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User findByIdAndUpdate(UUID userId, UserDTO body) {
        User trovato = this.findById(userId);

        if (!trovato.getEmail().equals(body.email()) && this.userRepository.existsByEmail(body.email())) {
            throw new Conflict("Email già in utilizzo") {
            };
        }

        trovato.setUsername(body.username());
        trovato.setEmail(body.email());
        trovato.setPassword(this.bcrypt.encode(body.password()));
        trovato.setRole(body.role());

        return this.userRepository.save(trovato);
    }

    public void findByIdAndDelete(UUID userId) {
        User trovato = this.findById(userId);
        this.userRepository.delete(trovato);
    }
}
