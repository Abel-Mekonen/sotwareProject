package software.project.mainClasses;

// import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.password.PasswordEncoder;

import software.project.customValidation.UniqueEmail;
import software.project.customValidation.UniqueUsername;
// import Tutorial.mainClasses.User.Gender;
import software.project.mainClasses.User.Location;
import lombok.Data;

@Data
public class RegistrationForm {



    
    @NotBlank(message="first name is required")  
    private String firstName;


    @NotBlank(message="last name is required")  
    private String lastName;


    @NotBlank(message="username is required")
    @UniqueUsername  
    private String username;

    @Email(message = "email must include @")
    @NotBlank(message="email is required")
    @UniqueEmail  
    private String email;

    private String photos;


    @NotBlank(message="phone number is required")  
    private String phone;


    
    @NotBlank(message="password is required")  
    private String password;


    // private Gender gender;


    private Location location;
    
    public User toUser(PasswordEncoder encoder){
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPhotos(photos);
        user.setPassword(encoder.encode(password));
        // user.setGender(gender);
        user.setLocation(location);
        return user;
    }

}
