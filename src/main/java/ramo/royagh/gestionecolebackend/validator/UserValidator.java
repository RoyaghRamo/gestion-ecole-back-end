package ramo.royagh.gestionecolebackend.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ramo.royagh.gestionecolebackend.entities.User;

@Component
public class UserValidator implements Validator {

    // In this method, we're going to define the class that we're going to support with this validator
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    // In this method, we're
    @Override
    public void validate(Object object, Errors errors) {

        User user = (User) object;

        if(user.getPassword().length() < 6){
            errors.rejectValue("password",
                    "Length",
                    "Le mot de passe doit avoir plus que 6 caractères");
        }

        if(!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("confirmPassword",
                    "Match",
                    "Les mots de passe doivent correspondre");
        }

    }
}
