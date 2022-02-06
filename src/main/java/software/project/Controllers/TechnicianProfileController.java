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
import software.project.mainClasses.Request.Status;
import software.project.repository.RequestRepo;
import software.project.service.TechnicianProfileService;

@Data
@AllArgsConstructor
@Controller
public class TechnicianProfileController {
    @Autowired
    private final TechnicianProfileService technicianProfileService;

    @Autowired
    private final RequestRepo reqRepo;

    @GetMapping("/displayTechnicianProfile")
    public ModelAndView viewTechnicianProfile(@AuthenticationPrincipal User user) { 
        return technicianProfileService.viewTechnicianProfile(user);
    }

    @PostMapping("/displayTechnicianProfile/{id}")
    public String viewTechnicianProfile(@AuthenticationPrincipal User user, @PathVariable long id, @RequestParam Status status) { 
        try {
            reqRepo.findById(id).ifPresent(req -> {
                req.setStatus(status);
                reqRepo.save(req);
            });
            return "redirect:/displayTechnicianProfile?success";
        } catch (Exception e) {

        }
        return "redirect:/displayTechnicianProfile?error";

    }

    @GetMapping("/editTechnicianProfile")
    public ModelAndView editTechnicianProfile(@RequestParam Long userId, @RequestParam Long technicianId, UserHelper userHelper ){ 
        return technicianProfileService.editTechnicianProfile(userId, technicianId, userHelper);
    }
    
    
    @PostMapping("/savetechnicianprofile")
    public String saveTutor(@AuthenticationPrincipal User user, @ModelAttribute UserHelper userHelper, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        return technicianProfileService.saveTutor(user, userHelper, multipartFile);
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
