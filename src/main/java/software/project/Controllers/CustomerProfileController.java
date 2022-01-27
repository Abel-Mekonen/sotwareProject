package software.project.Controllers;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


import lombok.AllArgsConstructor;
import lombok.Data;
import software.project.mainClasses.User;
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

    


    
}
