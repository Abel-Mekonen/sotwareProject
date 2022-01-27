package software.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import lombok.Data;
import software.project.mainClasses.Customer;
import software.project.mainClasses.SearchTutor;
import software.project.mainClasses.Technician;
import software.project.mainClasses.User;
import software.project.repository.TechnicianRepository;

@Data
@AllArgsConstructor
public class OrderService {
    @Autowired
    private final TechnicianRepository technicianRepository;
    


    public ModelAndView search(@AuthenticationPrincipal User user, @ModelAttribute SearchTutor searchTutor) {
        
        // Customer customer = user.getCustomerProfile();
        ModelAndView model = new ModelAndView("orderPage");
        List<Technician> technicians = technicianRepository.searchByDevice(searchTutor.getDevice());
        model.addObject("Query", new SearchTutor());
        model.addObject("technicians", technicians);
        return model;
    }
}
