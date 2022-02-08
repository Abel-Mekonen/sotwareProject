package software.project.Controllers;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import software.project.mainClasses.ConfrimEmail;
import software.project.mainClasses.User;
import software.project.mainClasses.UserHelper;
import software.project.repository.ConfirmEmailRepository;
import software.project.service.CustomerProfileService;
import software.project.service.SendMail;

@Data
@AllArgsConstructor
@Controller
public class CustomerProfileController {
    @Autowired
    private final CustomerProfileService customerProfileService;

    @Autowired
    private final ConfirmEmailRepository emailRepo;
    private SendMail sender;

    @GetMapping("/displayCustomerProfile")
    public String viewTuteeProfile(@AuthenticationPrincipal User user, Model model) {
        return customerProfileService.viewCustomerProfile(user, model);
    }

    @GetMapping("/resendEmail")
    public String resendEmail(@RequestParam("mail") String mail, Model model) {
        model.addAttribute("mail", mail);
        return "resendEmail";
    }

    @PostMapping("/resendEmail")
    public String resendMail(@RequestParam("mail") String mail, RedirectAttributes ra) {
        ConfrimEmail emailEntity = emailRepo.findByUserEmail(mail);
        if (emailEntity != null) {
            try {
                String link = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
                        + "/confirmEmail?id="
                        + emailEntity.getEmailconfirm();
                String confirmEmailText = "BTHY Electronic Maintenance\n Thanks for reqistering to BTHY system \n please click the following link to confirm your Email"
                        + link;
                sender.sendSimpleMessage(mail, "RESEND--BTHY Electronics Email Confirmation Link--RESEND",
                        confirmEmailText);
                ra.addFlashAttribute("success", "Email resend success, please check your mail.");
                return "redirect:/resendEmail?success=" + UUID.randomUUID().toString() + "&mail=" + mail + "&"
                        + UUID.randomUUID().toString();
            } catch (Exception e) {
                ra.addFlashAttribute("error", "can not send an email, please try again!");
                return "redirect:/resendEmail?error=" + UUID.randomUUID().toString() + "&mail=" + mail + "&"
                        + UUID.randomUUID().toString();
            }

        }
        ra.addFlashAttribute("error", "can not send an email, please try again!");
        return "redirect:/resendEmail?error";
    }

    @GetMapping("/editCustomerProfile")
    public ModelAndView editCustomerProfile(@RequestParam Long userId, @RequestParam Long customerId,
            UserHelper userHelper) {
        return customerProfileService.editCustomerProfile(userId, customerId, userHelper);
    }

    @PostMapping("/saveprofile")
    public String saveCustomer(@AuthenticationPrincipal User user, @ModelAttribute UserHelper userHelper,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {
        return customerProfileService.saveCustomer(user, userHelper, multipartFile);
    }

    @PostMapping("/deleteCustomer")
    public String deleteCustomer(@RequestParam Long technicianId) {
        return customerProfileService.deleteUsers(technicianId);
    }

    @PostMapping("/deleteCustomerAccount/{id}")
    public ModelAndView deleteCustomerAccount(@PathVariable String id) {
        return new ModelAndView("deleteCustomerAccount", "userId", id);
    }
}