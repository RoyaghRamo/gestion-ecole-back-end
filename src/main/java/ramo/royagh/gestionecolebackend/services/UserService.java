package ramo.royagh.gestionecolebackend.services;

// Register a user, then we can try to log in

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ramo.royagh.gestionecolebackend.entities.User;
import ramo.royagh.gestionecolebackend.exceptions.UsernameAlreadyExistsException;
import ramo.royagh.gestionecolebackend.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // This BCryptPasswordEncoder comes with Spring Security
    // This helps us encode our passwords so that we don't store
    // Strings or anything that's readable from the database
    // BCryptPasswordEncoder isn't a bean, so we can't autowire it
    // Therefore the following :
    // Go to the main application and add create a bean corresponding to BCryptPasswordEncoder (*)
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser){

        try {
            // Happy Path
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            // Username needs to be unique (customException)
            newUser.setUsername(newUser.getUsername());
            // We don't persist or show the confirmPassword
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);
        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username "+newUser.getUsername()+" already exists");
        }
    }

}
