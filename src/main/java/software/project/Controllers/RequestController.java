package software.project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import lombok.Data;
import software.project.mainClasses.SearchTechnician;
// import software.project.mainClasses.SearchTutor;
import software.project.mainClasses.User;
import software.project.service.OrderService;
@Data
@AllArgsConstructor
@Controller
public class RequestController {
    @Autowired
    private final OrderService orderService;

    @GetMapping("order/orderdevice")
    public ModelAndView makeOrder(@AuthenticationPrincipal User user, @ModelAttribute SearchTechnician searchTechnician ){

        ModelAndView model = new ModelAndView("order");
        model.addObject("Query", new SearchTechnician());
        return model;
    }
    @PostMapping(path = "/order/ListOfTechnician")
    public ModelAndView searchTechnician(@AuthenticationPrincipal User user, @ModelAttribute SearchTechnician searchTechnician){
        return orderService.search(user, searchTechnician);
    }

    
}
