package software.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import lombok.Data;
import software.project.mainClasses.Customer;
import software.project.mainClasses.SearchTechnician;
import software.project.mainClasses.Technician;
import software.project.mainClasses.User;
import software.project.repository.TechnicianRepository;

@Data
@AllArgsConstructor

@Service
public class OrderService {
    @Autowired
    private final TechnicianRepository technicianRepository;

    public ModelAndView search(@AuthenticationPrincipal User user, @ModelAttribute SearchTechnician searchTechnician) {
        

        ModelAndView model = new ModelAndView("order");

        List<Technician> technicians = technicianRepository.searchByDevice(searchTechnician.getDevice());
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println(technicians);
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");

       


        model.addObject("technicians", technicians);
        model.addObject("Query", searchTechnician);
        return model;
        
    }
}
