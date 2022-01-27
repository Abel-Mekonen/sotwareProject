package software.project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import lombok.Data;
import software.project.mainClasses.SearchTutor;
import software.project.mainClasses.User;

import software.project.service.OrderService;
@Data
@AllArgsConstructor
public class OrderController {
    @Autowired
    private final OrderService orderService;

    @GetMapping("/order")
    public ModelAndView viewTuteeProfile(@AuthenticationPrincipal User user) { 
        ModelAndView mav = new ModelAndView("orderPage");
        mav.addObject("Query", new SearchTutor());
        return mav;
    }

    @PostMapping(path = "/Searched")
    public ModelAndView search(@AuthenticationPrincipal User user, @ModelAttribute SearchTutor searchTutor) {
        return orderService.search(user, searchTutor);
    }

    
}