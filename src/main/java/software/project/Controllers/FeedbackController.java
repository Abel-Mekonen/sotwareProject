package software.project.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import software.project.mainClasses.Feedback;
import software.project.repository.FeedbackRepository;
import software.project.service.LoggedUser;

@Controller
public class FeedbackController {


    @Autowired
    private FeedbackRepository feedbackRepo;

    @Autowired
    private LoggedUser loggedUser;

    @GetMapping("/feedback")
    public String feedback(Model model, @ModelAttribute("feedback") Feedback feedback ){
        return "feedback";
    }

    @PostMapping("/feedback")
    public String submitFeedback(@Valid @ModelAttribute("feedback") Feedback feedback, BindingResult bResult, RedirectAttributes ra){
        if(bResult.hasErrors()){
            ra.addFlashAttribute("error", bResult.getFieldError().getDefaultMessage());
            return "redirect:/feedback?error";
        }
        if(loggedUser.get_User() != null){
            feedback.setSender(loggedUser.get_User());
        }else{
            return "redirect:/login";
        }
        try {
            feedbackRepo.save(feedback);
            ra.addFlashAttribute("success", "Feedback sent successfully");
            return "redirect:/feedback?success";
        } catch (Exception e) {
            ra.addFlashAttribute("error", "error, could not send feedback");
            return "redirect:/feedback?error";
        }
    }
}
