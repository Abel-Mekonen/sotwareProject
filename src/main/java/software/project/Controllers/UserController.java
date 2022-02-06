package software.project.Controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

// import software.project.*;
import software.project.mainClasses.RegistrationForm;
import software.project.service.UserService;
import lombok.AllArgsConstructor;
@Controller
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/error")
    public ModelAndView error(){
        ModelAndView model=new ModelAndView("errorPage");
        return model;
    }

    @GetMapping("/techniciansignup")
    public String technicianRegisterForm(@ModelAttribute RegistrationForm registrationForm) {
        return "technicianSignup";
    }

    @PostMapping("/saveTechnician")
    public String saveTechnician(@Valid @ModelAttribute RegistrationForm registrationForm, Errors errors, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (errors.hasErrors()){
            return "technicianSignup";
        }
        return userService.saveTechnician(registrationForm, multipartFile);
    }

    @GetMapping("/customersignup")
    public String customerRegisterForm(@ModelAttribute RegistrationForm registrationForm) {
        return "customerSignup";
    }

    @PostMapping("/savecustomer")
    public String saveCustomer(@Valid @ModelAttribute RegistrationForm registrationForm, Errors errors, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (errors.hasErrors()){
            return "customerSignup";
        }
    
        return userService.saveCustomer(registrationForm, multipartFile);
    }
}
