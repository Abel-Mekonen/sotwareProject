package software.project.Controllers;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import software.project.mainClasses.ConfrimEmail;
// import software.project.*;
import software.project.mainClasses.RegistrationForm;
import software.project.mainClasses.User;
import software.project.repository.ConfirmEmailRepository;
import software.project.repository.UserRepository;
import software.project.service.SendMail;
import software.project.service.UserService;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;

@Controller
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final ConfirmEmailRepository confirmRepo;

    @Autowired
    private final UserRepository userRepo;

    @Autowired
    private final SendMail sender;
    @Autowired
    private final ConfirmEmailRepository emailRepo;

    @Autowired
    private final PasswordEncoder encoder;

    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView model = new ModelAndView("errorPage");
        return model;
    }

    @Transactional
    @GetMapping("/confirmEmail")
    public String confirmEmail(@RequestParam("id") String emailconfirm, RedirectAttributes ra) {
        try {
            ConfrimEmail confirm = confirmRepo.findByEmailconfirm(emailconfirm);
            User user = confirm.getUser();
            user.setMailConfrimed(true);
            userRepo.save(user);
            confirmRepo.delete(confirm);
            ra.addAttribute("success", "Email Confirmed Successfull, please login.");
            return "redirect:/login?success";
        } catch (Exception e) {
            ra.addAttribute("error", "could not confirm email address.\n please try again.");
            return "redirect:/resendEmail?error";
        }
    }

    @GetMapping("/forgetPassword")
    public String forgetpass() {
        return "forgetPassword";
    }

    @PostMapping("/forgetPassword")
    public String forgetpassProccess(@RequestParam("mail") String emailconfirm, RedirectAttributes ra) {
        try {
            User user = userRepo.findByEmail(emailconfirm);
            String userEmail = user.getEmail();
            ConfrimEmail mail = new ConfrimEmail();
            mail.setUser(user);

            String link = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/resetEmail?id="
                    + mail.getEmailconfirm() + "&" + RandomString.make(100).toLowerCase();
            String confirmEmailText = "BTHY Electronic Maintenance \n thanks for reqistering to BTHY system \n please click the link below to reset your password!"
                    + link;
            sender.sendSimpleMessage(emailconfirm, "BTHY Electronics Email Password Reset",
                    confirmEmailText);
            emailRepo.save(mail);
            ra.addAttribute("success", "Email Sent successfully.");
            return "redirect:/forgetPassword?success";
        } catch (Exception e) {
            ra.addAttribute("error", "could not send an email.\n please try again.");
            return "redirect:/forgetPassword?error";
        }
    }

    @GetMapping("/resetEmail")
    public String resetMail(@RequestParam("id") String id, Model model) {
        try {
            ConfrimEmail confirm = confirmRepo.findByEmailconfirm(id);
            User user = confirm.getUser();
            model.addAttribute("id", user.getId());
            return "resetEmail";
        } catch (Exception e) {
            model.addAttribute("no", "could not find.");
            return "resetEmail";
        }
    }

    @GetMapping("/resetEmail")
    public String resetMailProccessor(@RequestParam("id") long id, @RequestParam("password") String password,
            Model model, RedirectAttributes ra) {
        try {
            User user = userRepo.getById(id);
            user.setPassword(encoder.encode(password));
            userRepo.save(user);
            ra.addFlashAttribute("success", "Password Reseted Successfully, Please Login.");
            return "redirect:/login?success";
        } catch (Exception e) {
            model.addAttribute("error", "Error please try again.");
            return "redirect:/resetEmail?error";
        }
    }

    @GetMapping("/techniciansignup")
    public String technicianRegisterForm(@ModelAttribute RegistrationForm registrationForm) {
        return "technicianSignup";
    }

    @PostMapping("/saveTechnician")
    public String saveTechnician(@Valid @ModelAttribute RegistrationForm registrationForm, Errors errors,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (errors.hasErrors()) {
            return "technicianSignup";
        }
        return userService.saveTechnician(registrationForm, multipartFile);
    }

    @GetMapping("/customersignup")
    public String customerRegisterForm(@ModelAttribute RegistrationForm registrationForm) {
        return "customerSignup";
    }

    @PostMapping("/savecustomer")
    public String saveCustomer(@Valid @ModelAttribute RegistrationForm registrationForm, Errors errors,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (errors.hasErrors()) {
            return "customerSignup";
        }
        return userService.saveCustomer(registrationForm, multipartFile);
    }
}
