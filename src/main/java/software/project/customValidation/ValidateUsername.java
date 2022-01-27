package software.project.customValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import software.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class ValidateUsername implements ConstraintValidator<UniqueUsername,String> {

        @Autowired
        private UserRepository userRepository;
    
    
    
        @Override
        public boolean isValid(String username, ConstraintValidatorContext context) {
            boolean valid=userRepository.existsByUsername(username);
            return !valid ;
        }
    
}
