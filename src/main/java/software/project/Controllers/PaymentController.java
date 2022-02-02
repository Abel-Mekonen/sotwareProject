package software.project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import software.project.mainClasses.Payment;
import software.project.repository.PaymentRepository;
import software.project.service.LoggedUser;

@Controller
public class PaymentController {


    @Autowired
    private PaymentRepository paymentRepo;

    // @Autowired
    // private DeviceService deviceSer;

    @Autowired
    private LoggedUser loggedUser;

    @GetMapping("/payment")
    public String payment(@RequestParam(name = "id") long id, Model model, @ModelAttribute("payment") Payment payment ){
        model.addAttribute("id", id);
        return "payment";
    }

    @PostMapping("/payment/{id}")
    public String submitFeedback(@PathVariable("id") long id, @ModelAttribute("payment") Payment payment, RedirectAttributes ra ){
        if(loggedUser.get_User() != null){
            payment.setPayer(loggedUser.get_User());
        }else{
            return "redirect:/login";
        }
        try {
            paymentRepo.save(payment);
            ra.addFlashAttribute("success", "Payment information saved successfully");
            return "redirect:/feedback?success";
        } catch (Exception e) {
            ra.addFlashAttribute("error", "error, could not send Payment information");
            return "redirect:/feedback?error";
        }
    }
}
