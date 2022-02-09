package software.project.service;

import java.io.IOException;
import java.util.Optional;

import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import lombok.Data;
// import lombok.NoArgsConstructor;
import software.project.mainClasses.Customer;
import software.project.mainClasses.FileUploadUtil;
import software.project.mainClasses.User;
import software.project.mainClasses.UserHelper;
import software.project.repository.CustomerRepository;
import software.project.repository.UserRepository;

@Data
@Service

public class CustomerProfileService {
    @Autowired
    private final CustomerRepository customerRepository;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public String viewCustomerProfile(@AuthenticationPrincipal User user, Model model) {

        // ModelAndView mav = new ModelAndView("displayCustomer");
        // Customer cProfile = user.getCustomerProfile();
        if (!user.isMailConfrimed()) {
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            model.addAttribute("mail", user.getEmail());
            model.addAttribute("error", "your email address" + user.getEmail()
                    + " is not confrimed,\n please check your email or resend to confirm");
            return "resendEmail";
        }
        // model.addAttribute("CustomerProfile", cProfile);
        model.addAttribute("CurrentUser", user);

        return "displayCustomer";
    }

    public ModelAndView editCustomerProfile(@RequestParam Long userId, @RequestParam Long customerId,
            UserHelper userHelper) {
        ModelAndView mav = new ModelAndView("editCustomerProfile");
        User custUser = userRepo.findById(userId).get();
        // Customer tProfile = customerRepository.findById(customerId).get();

        userHelper.setFirstName(custUser.getFirstName());
        userHelper.setLastName(custUser.getLastName());
        userHelper.setEmail(custUser.getEmail());
        // userHelper.setGender(custUser.getGender());
        userHelper.setLocation(custUser.getLocation());
        userHelper.setPhone(custUser.getPhone());
        userHelper.setUsername(custUser.getUsername());
        // userHelper.setDevice(tProfile.getDevice());
        // userHelper.setLevel(tProfile.getLevel());
        // userHelper.setDescription(tProfile.getDescription());
        userHelper.setPassword(custUser.getPassword());

        mav.addObject("customerProfile", userHelper);
        return mav;
    }

    public String saveCustomer(@AuthenticationPrincipal User user, UserHelper userHelper, MultipartFile multipartFile) {
        var profile = user.getCustomerProfile();

        String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
        userHelper.setPhotos(fileName);
        // Optional<User> pic = userRepo.findById(user.getId());
        // if (pic==null){
        // user.setPhotos("");

        // }

        user.setFirstName(userHelper.getFirstName());
        user.setLastName(userHelper.getLastName());
        user.setPhotos(userHelper.getPhotos());
        user.setEmail(userHelper.getEmail());
        user.setUsername(userHelper.getUsername());
        user.setPhone(userHelper.getPhone());
        user.setLocation(userHelper.getLocation());
        user.setPassword(encoder.encode(userHelper.getPassword()));

        User savedUser = userRepo.save(user);

        String uploadDir = "user-photos/" + savedUser.getId();
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        customerRepository.save(profile);
        return "redirect:/displayCustomerProfile";
    }

    public String deleteUsers(Long customerId) {
        userRepo.deleteById(customerId);
        return "redirect:/login";
    }

}