package software.project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import software.project.service.AdminDashboardService;
import software.project.mainClasses.User;
import software.project.mainClasses.UserHelper;
// import software.project.mainClasses.UserHelper;
import software.project.mainClasses.AdminAddUserForm;
import software.project.repository.UserRepository;
// import software.project.service.AdminDashboardService;

import java.util.*;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AdminDashboardController {
    @Autowired
    private final UserRepository userRepo;
    private final AdminDashboardService adminDashboardService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/adminDashBoard")
    public String adminRoot(Model model) {
        int messages = adminDashboardService.unseeMessagecounter();
        model.addAttribute("messages", messages);
        return "adminRoot";
    }

    @GetMapping("/adminDashBoard/users")
    public ModelAndView userDisplay() {
        ModelAndView adminModel = new ModelAndView("adminDashboard.html");
        List<User> list = userRepo.findAll();
        adminModel.addObject("ListOfUsers", list);
        return adminModel;
    }

    // @GetMapping("/adminDashBoard/addUsers")
    // public ModelAndView addUser(){
    //     ModelAndView mav = new ModelAndView("addUserForm");
    //     User newUser = new User();
    //     mav.addObject("User", newUser);
    //     return mav;
    // } 

    @GetMapping("/adminDashBoard/users/addUsers") 
    public String addUser(@ModelAttribute AdminAddUserForm adminAddUserForm) {
        return adminDashboardService.addUser(adminAddUserForm);
    }

    @PostMapping("/adminDashBoard/users/saveUser")
    public String saveCustomer(@ModelAttribute AdminAddUserForm adminAddUserForm){
        var user = adminAddUserForm.toUser(passwordEncoder);
        userRepo.save(user);
        return "redirect:/adminDashBoard/users";
    }

    @GetMapping("/adminDashBoard/users/updateUsers")
    public ModelAndView showUpdateForm(@RequestParam Long userId, @ModelAttribute UserHelper userHelper){
        return adminDashboardService.showUpdateForm(userId, userHelper);
    }


    @GetMapping("/adminDashBoard/users/deleteUsers")
    public String deleteUsers(@RequestParam Long userId){
        return adminDashboardService.deleteUsers(userId);
    
    }
    
    @PostMapping("adminDashBoard/users/saveprofile")
    public String saveTutor(@RequestParam Long userId,User user, @ModelAttribute("EditByAdmin") UserHelper userHelper){
        return adminDashboardService.saveUser(userId,user,userHelper);
    }

    @GetMapping("adminDashBoard/messages")
    public String messageList(Model model){
        return adminDashboardService.messageLists(model);
    }

    @GetMapping("adminDashBoard/message/{id}")
    public String messageDetail(@PathVariable("id") long id , Model model, RedirectAttributes ra){
        return adminDashboardService.messageDetail(id, model, ra);
    }    
}