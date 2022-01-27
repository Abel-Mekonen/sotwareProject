package software.project.customValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import  software.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class ValidateEmail implements ConstraintValidator<UniqueEmail,String> {

    @Autowired
    private UserRepository userRepository;



    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        boolean valid=userRepository.existsByEmail(email);
        return !valid ;
    }
}
