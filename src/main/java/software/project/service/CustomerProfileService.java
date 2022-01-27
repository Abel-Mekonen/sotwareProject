package software.project.service;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import software.project.mainClasses.Customer;
import software.project.mainClasses.User;

@Service
public class CustomerProfileService {
    
    public ModelAndView viewCustomerProfile(@AuthenticationPrincipal User user) {

        ModelAndView mav = new ModelAndView("displayCustomer");
        Customer cProfile = user.getCustomerProfile();

        mav.addObject("CustomerProfile", cProfile);
        return mav;
    } 

}
