package software.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.project.mainClasses.Customer;
import software.project.mainClasses.User;
import software.project.mainClasses.UserHelper;
import software.project.repository.CustomerRepository;
import software.project.repository.UserRepository;

@Data
@Service

public class CustomerProfileService {
    @Autowired
    private final CustomerRepository customerRepository;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    
    public ModelAndView viewCustomerProfile(@AuthenticationPrincipal User user) {

        ModelAndView mav = new ModelAndView("displayCustomer");
        Customer cProfile = user.getCustomerProfile();

        mav.addObject("CustomerProfile", cProfile);
        return mav;
    } 
    
    public ModelAndView editCustomerProfile(@RequestParam Long userId, @RequestParam  Long customerId,  UserHelper userHelper){
        ModelAndView mav = new ModelAndView("editCustomerProfile");
        User custUser = userRepo.findById(userId).get();
        Customer tProfile = customerRepository.findById(customerId).get();

        userHelper.setFirstName(custUser.getFirstName());
        userHelper.setLastName(custUser.getLastName());
        userHelper.setEmail(custUser.getEmail());
        // userHelper.setGender(custUser.getGender());
        userHelper.setLocation(custUser.getLocation());
        userHelper.setPhone(custUser.getPhone());
        userHelper.setUsername(custUser.getUsername());
        // userHelper.setDevice(tProfile.getDevice());
        // userHelper.setLevel(tProfile.getLevel());
        // userHelper.setDescription(tProfile.getDescription());
        userHelper.setPassword(custUser.getPassword());

    
        mav.addObject("customerProfile", userHelper);
        return mav;
    }

    public String saveCustomer(@AuthenticationPrincipal User user, UserHelper userHelper){
        var profile = user.getCustomerProfile();

        // profile.setCourse(userHelper.getCourse());
        
        user.setFirstName(userHelper.getFirstName());
        user.setLastName(userHelper.getLastName());
        user.setEmail(userHelper.getEmail());
        user.setUsername(userHelper.getUsername());
        user.setPhone(userHelper.getPhone());
        user.setLocation(userHelper.getLocation());
        user.setPassword(encoder.encode(userHelper.getPassword()));
        userRepo.save(user);
        customerRepository.save(profile);
        return "redirect:/displayCustomerProfile";
    }

    public String deleteUsers(Long customerId) {
        userRepo.deleteById(customerId);
        return "redirect:/login";
    }



}
