package software.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.Data;
// import software.project.mainClasses.Customer;
import software.project.mainClasses.Technician;
import software.project.mainClasses.User;
import software.project.mainClasses.UserHelper;
import software.project.repository.CustomerRepository;
import software.project.repository.TechnicianRepository;
import software.project.repository.UserRepository;
import software.project.repository.RequestRepo;

@Data

@Service
public class TechnicianProfileService {
    
    @Autowired
    private final TechnicianRepository profileRepository;
    private final UserRepository userRepo;
    // private final CustomerRepository tuteeProfileRepository;
    private final RequestRepo requestRepo;
    private final PasswordEncoder encoder;

    
    public ModelAndView viewTechnicianProfile(@AuthenticationPrincipal User user) {

        ModelAndView mav = new ModelAndView("displayTechnician");
        Technician tProfile = user.getTechnicianProfile();
        mav.addObject("TechnicianProfile", tProfile);
        return mav;
    } 


    public ModelAndView editTutorProfile(@RequestParam Long userId, @RequestParam  Long tutorId,  UserHelper userHelper){
        ModelAndView mav = new ModelAndView("editTechnicianProfile");
        User techUser = userRepo.findById(userId).get();
        Technician tProfile = profileRepository.findById(tutorId).get();

        userHelper.setFirstName(techUser.getFirstName());
        userHelper.setLastName(techUser.getLastName());
        userHelper.setEmail(techUser.getEmail());
        // userHelper.setGender(techUser.getGender());
        userHelper.setLocation(techUser.getLocation());
        userHelper.setPhone(techUser.getPhone());
        userHelper.setUsername(techUser.getUsername());
        userHelper.setDevice(tProfile.getDevice());
        // userHelper.setLevel(tProfile.getLevel());
        userHelper.setDescription(tProfile.getDescription());
        userHelper.setPassword(techUser.getPassword());

    
        mav.addObject("technicianProfile", userHelper);
        return mav;
    }

    public String saveTutor(@AuthenticationPrincipal User user, UserHelper userHelper){
        var profile = user.getTechnicianProfile();

        // profile.setCourse(userHelper.getCourse());
        profile.setDescription(userHelper.getDescription());
        profile.setDevice(userHelper.getDevice());
        user.setFirstName(userHelper.getFirstName());
        user.setLastName(userHelper.getLastName());
        user.setEmail(userHelper.getEmail());
        user.setUsername(userHelper.getUsername());
        user.setPhone(userHelper.getPhone());
        user.setLocation(userHelper.getLocation());
        user.setPassword(encoder.encode(userHelper.getPassword()));
        userRepo.save(user);
        profileRepository.save(profile);
        return "redirect:/displayTechnicianProfile";
    }

    public String deleteUsers(Long technicianId) {
        userRepo.deleteById(technicianId);
        return "redirect:/login";
    }

}
