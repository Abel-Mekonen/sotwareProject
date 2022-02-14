package software.project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.support.BeanDefinitionDsl.Role;
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
import software.project.mainClasses.User.Role;

import software.project.mainClasses.UserHelper;
// import software.project.mainClasses.UserHelper;
import software.project.mainClasses.AdminAddUserForm;
import software.project.mainClasses.Customer;
import software.project.mainClasses.Technician;
import software.project.repository.CustomerRepository;
import software.project.repository.TechnicianRepository;
import software.project.repository.UserRepository;
// import software.project.service.AdminDashboardService;

import java.lang.ProcessBuilder.Redirect;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.var;

@Controller
@AllArgsConstructor
public class AdminDashboardController {
    @Autowired
    private final UserRepository userRepo;
    private final AdminDashboardService adminDashboardService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private TechnicianRepository technicianRepository;

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
        
     
        user.setMailConfrimed(true);
        userRepo.save(user);

        var role = user.getRole();
        if (role == (Role.CUSTOMER)){
            var cprofile = new Customer();
            cprofile.setUser(user);
            customerRepository.save(cprofile);

        }
        else if(role == Role.TECHNICIAN){
            var tprofile = new Technician();
            tprofile.setUser(user);
            technicianRepository.save(tprofile);

        }

        return "redirect:/adminDashBoard";
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
    
    
    @GetMapping("adminDashBoard/ApprovedTechnician")
    public ModelAndView approvedTechnician(){
        return adminDashboardService.approvedTechnician();
    }

    @GetMapping("/adminDashBoard/ApprovedTechnicians")
    public String approved(@RequestParam("id") Long id){

        Optional<Technician> techs = technicianRepository.findById(id);
        techs.ifPresent(tech ->{
            tech.setApproved(false);
            technicianRepository.save(tech);              
        });
        return "redirect:/adminDashBoard/ApprovedTechnician";
    }


    @GetMapping("adminDashBoard/notApprovedTechnician")
    public ModelAndView unApprovedTechnician(){
        return adminDashboardService.unApprovedTechnician();
    }
    @GetMapping("/adminDashBoard/notApprovedTechnicians")
    public String notapproved(@RequestParam("id") Long id){

        Optional<Technician> techs = technicianRepository.findById(id);
        techs.ifPresent(tech ->{
            tech.setApproved(true);
            technicianRepository.save(tech);              
        });
        return "redirect:/adminDashBoard/notApprovedTechnician";
    }

}
