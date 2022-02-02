package software.project.Controllers;




import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import lombok.AllArgsConstructor;
import lombok.Data;
import software.project.mainClasses.User;
import software.project.mainClasses.UserHelper;
import software.project.service.TechnicianProfileService;

@Data
@AllArgsConstructor
@Controller
public class TechnicianProfileController {
    @Autowired
    private final TechnicianProfileService technicianProfileService;

    @GetMapping("/displayTechnicianProfile")
    public ModelAndView viewTechnicianProfile(@AuthenticationPrincipal User user) { 
        return technicianProfileService.viewTechnicianProfile(user);
    }


    @GetMapping("/editTechnicianProfile")
    public ModelAndView editTechnicianProfile(@RequestParam Long userId, @RequestParam Long technicianId, UserHelper userHelper ){ 
        return technicianProfileService.editTechnicianProfile(userId, technicianId, userHelper);
    }
    
    
    @PostMapping("/savetechnicianprofile")
    public String saveTutor(@AuthenticationPrincipal User user, @ModelAttribute UserHelper userHelper){
        return technicianProfileService.saveTutor(user, userHelper);
    }

    @PostMapping("/deleteTechnician")
    public String deleteTutee(@RequestParam Long technicianId){
        return technicianProfileService.deleteUsers(technicianId);
    }
    
    @PostMapping("/deleteTechnicianAccount/{id}")
    public ModelAndView deleteTutorAccount(@PathVariable String id) {
        return new ModelAndView("deleteTechnicianAccount", "userId", id);
    }
    

}
