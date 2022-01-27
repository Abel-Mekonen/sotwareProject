package software.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import software.project.mainClasses.AdminAddUserForm;
import software.project.mainClasses.Feedback;
import software.project.mainClasses.User;
import software.project.mainClasses.UserHelper;
import software.project.repository.FeedbackRepository;
// import software.project.mainClasses.UserHelper;
import software.project.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
// @NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private FeedbackRepository messageRepo;

    private final PasswordEncoder encoder;

    public String addUser(@ModelAttribute AdminAddUserForm adminAddUserForm) {
        return "addUserForm";
    }

    public ModelAndView showUpdateForm(Long userId, UserHelper userHelper) {
        ModelAndView mav = new ModelAndView("editUser");
        mav.addObject("userId", userId);
        User user = userRepo.findById(userId).get();
        userHelper.setFirstName(user.getFirstName());
        userHelper.setLastName(user.getLastName());
        userHelper.setUsername(user.getUsername());
        userHelper.setEmail(user.getEmail());
        userHelper.setPhone(user.getPhone());
        userHelper.setPassword(user.getPassword());
        userHelper.setRole(user.getRole());
        mav.addObject("EditByAdmin", userHelper);

        return mav;

    }

    public String saveUser(Long userId, User user, UserHelper userHelper) {
        user = userRepo.findById(userId).get();
        user.setFirstName(userHelper.getFirstName());
        user.setLastName(userHelper.getLastName());
        user.setEmail(userHelper.getEmail());
        user.setUsername(userHelper.getUsername());
        user.setPhone(userHelper.getPhone());
        user.setLocation(userHelper.getLocation());
        user.setPassword(encoder.encode(userHelper.getPassword()));
        userRepo.save(user);
        return "redirect:/adminDashBoard/users";
    }

    public String deleteUsers(@RequestParam Long userId) {
        userRepo.deleteById(userId);
        return "redirect:/adminDashBoard/users";
    }

    public String messageLists(Model model){
        List<Feedback> messages = messageRepo.findAll();
        model.addAttribute("messages", messages);
        return "messagesList";
    }

    public int unseeMessagecounter(){
        List<Feedback> messages = messageRepo.findBySeen(false);
        return messages.size();
    }

    public String messageDetail(long id, Model model, RedirectAttributes ra) {
        Optional<Feedback> messageTemp = messageRepo.findById(id);
        if(!messageTemp.isPresent()) {
            ra.addFlashAttribute("error", "could not find the message");
            return "redirect:/adminDashBoard/messages";
        }
        Feedback message = messageTemp.get();
        if(message.getSender() != null){
            model.addAttribute("sender", message.getSender());
        }
        model.addAttribute("message", message);
        message.setSeen(true);
        messageRepo.save(message);
        return "messageDetail";
    }
}
