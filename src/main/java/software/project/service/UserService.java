package software.project.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import software.project.*;
import software.project.mainClasses.Customer;
import software.project.mainClasses.RegistrationForm;
import software.project.mainClasses.Technician;
import software.project.mainClasses.User.Role;
import software.project.repository.CustomerRepository;
import software.project.repository.TechnicianRepository;
import software.project.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
    @Autowired
    private final UserRepository userRepo;
    private final CustomerRepository customerRepository;
    private final TechnicianRepository technicianRepository;
    private final PasswordEncoder passwordEncoder;

    public String saveTechnician(RegistrationForm registrationForm){
        var user = registrationForm.toUser(passwordEncoder);
        user.setRole(Role.TECHNICIAN);
        userRepo.save(user);
        var profile = new Technician();
        profile.setUser(user);
        technicianRepository.save(profile);
        return "redirect:/login";
    }

    public String saveCustomer(RegistrationForm registrationForm){

        var user = registrationForm.toUser(passwordEncoder);
        user.setRole(Role.CUSTOMER);
        userRepo.save(user);
        var profile = new Customer();
        profile.setUser(user);
        customerRepository.save(profile);
        return "redirect:/login";

    }

    
}
