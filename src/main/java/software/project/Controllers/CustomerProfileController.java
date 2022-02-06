package software.project.Controllers;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import lombok.AllArgsConstructor;
import lombok.Data;
import software.project.mainClasses.User;
import software.project.mainClasses.UserHelper;
import software.project.service.CustomerProfileService;

@Data
@AllArgsConstructor
@Controller
public class CustomerProfileController {
    @Autowired
    private final CustomerProfileService customerProfileService;

    @GetMapping("/displayCustomerProfile")
    public ModelAndView viewTuteeProfile(@AuthenticationPrincipal User user) { 
        return customerProfileService.viewCustomerProfile(user);
    }

    @GetMapping("/editCustomerProfile")
    public ModelAndView editCustomerProfile(@RequestParam Long userId, @RequestParam Long customerId, UserHelper userHelper ){ 
        return customerProfileService.editCustomerProfile(userId, customerId, userHelper);
    }

    @PostMapping("/saveprofile")
    public String saveCustomer(@AuthenticationPrincipal User user, @ModelAttribute UserHelper userHelper, @RequestParam("image") MultipartFile multipartFile) throws IOException{
        return customerProfileService.saveCustomer(user, userHelper, multipartFile );
    }

    @PostMapping("/deleteCustomer")
    public String deleteCustomer(@RequestParam Long technicianId){
        return customerProfileService.deleteUsers(technicianId);
    }
    
    @PostMapping("/deleteCustomerAccount/{id}")
    public ModelAndView deleteCustomerAccount(@PathVariable String id) {
        return new ModelAndView("deleteCustomerAccount", "userId", id);
    }
}