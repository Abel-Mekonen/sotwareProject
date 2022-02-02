package software.project.mainClasses;

import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import software.project.mainClasses.Technician.Device;
import software.project.mainClasses.Request.Status;


// import Tutorial.mainClasses.TutorProfile.Level;
// import Tutorial.mainClasses.TutorProfile;

import software.project.mainClasses.User.Location;
import software.project.mainClasses.User.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class RequestHelper {
    
    
    // @NotBlank(message="first name is required")  
    // private String firstName;


    // @NotBlank(message="last name is required")  
    // private String lastName;


    // @NotBlank(message="username is required")  
    // private String username;

    // @Email(message = "email must include @")
    // @NotBlank(message="email is required")  
    // private String email;


    // @NotBlank(message="phone number is required")  
    // private String phone;

    // private Long id;

    
    // @NotBlank(message="password is required")  
    // private String password;

    // private Device device;


    // private Location location;


    // private String description;


    // private Role role;
    // private Long customer;
    // private  Long techinician;
    // private Status status;

    // public User toUser(PasswordEncoder encoder){
    //     User user = new User();
    //     user.setId(id);
    //     user.setUsername(username);
    //     user.setFirstName(firstName);
    //     user.setLastName(lastName);
    //     user.setEmail(email);
    //     user.setPhone(phone);
    //     user.setPassword(password);
    //     user.setLocation(location);
    //     user.setRole(role);
    //     return user;
    // }
    // public Technician tProfile(){
    //     Technician tProfile = new Technician();
    //     tProfile.setDevice(device);
    //     tProfile.setDescription(description);
    //     return tProfile;
    // }
    // public Request request(){
    //     Request tRequest = new Request();
    //     tRequest.setCustomer(customer);
    //     tRequest.setTechinician(techinician);
    //     tRequest.setStatus(status);
    //     return tRequest;
    // } 

    private Customer customer;
    private Technician tech;
    private Request req;
    private User user;
    private Status status;


}
