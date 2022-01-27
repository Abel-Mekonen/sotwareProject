package software.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import lombok.Data;
import software.project.mainClasses.Customer;
import software.project.mainClasses.Request;
import software.project.mainClasses.SearchTechnician;
import software.project.mainClasses.Technician;
import software.project.mainClasses.User;
import software.project.mainClasses.Request.Status;
import software.project.repository.RequestRepo;
import software.project.repository.TechnicianRepository;

@Data
@AllArgsConstructor

@Service
public class OrderService {
    @Autowired
    private final TechnicianRepository technicianRepository;
    // private final TechnicianRepository technicianRepository;
    private final RequestRepo requestRepo;

    public ModelAndView search(@AuthenticationPrincipal User user, @ModelAttribute SearchTechnician searchTechnician) {
        

        ModelAndView model = new ModelAndView("order");

        List<Technician> technicians = technicianRepository.searchByDevice(searchTechnician.getDevice()); 


        model.addObject("technicians", technicians);
        model.addObject("Query", searchTechnician);
        return model;
        
    }

    
    public String sendRequest(@PathVariable String id, @AuthenticationPrincipal User user) {
        
        Technician currentTechnician = technicianRepository.findById(Long.parseLong(id)).get();
        Request rq = new Request();
        Customer custProfile = user.getCustomerProfile();
        rq.setStatus(Status.PENDING);
        rq.setCustomer(custProfile.getId());
        rq.setTechinician(currentTechnician.getId());
        requestRepo.save(rq);    
        return "redirect:/order/requestSuccess";

    }
}
