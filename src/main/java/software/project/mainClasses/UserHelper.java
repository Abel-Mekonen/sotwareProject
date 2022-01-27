package software.project.mainClasses;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.password.PasswordEncoder;

import software.project.mainClasses.Technician.Device;

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
public class UserHelper {

    

    @NotBlank(message="first name is required")  
    private String firstName;


    @NotBlank(message="last name is required")  
    private String lastName;


    @NotBlank(message="username is required")  
    private String username;

    @Email(message = "email must include @")
    @NotBlank(message="email is required")  
    private String email;


    @NotBlank(message="phone number is required")  
    private String phone;

    private Long id;

    
    @NotBlank(message="password is required")  
    private String password;

    private Device device;


    private Location location;


    private String description;


    private Role role;

    
    public User toUser(PasswordEncoder encoder){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setLocation(location);
        user.setRole(role);
        return user;
    }

    public Technician tProfile(){
        Technician tProfile = new Technician();
        tProfile.setDevice(device);
        tProfile.setDescription(description);
        return tProfile;
    }
}
