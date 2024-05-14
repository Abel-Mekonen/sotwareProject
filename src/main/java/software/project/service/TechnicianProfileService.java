package software.project.service;

import io.github.pixee.security.Filenames;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
// import java.util.Optional;

import lombok.Data;
// import software.project.mainClasses.Customer;
// import software.project.mainClasses.Customer;
import software.project.mainClasses.Technician;
import software.project.mainClasses.User;
import software.project.mainClasses.FileUploadUtil;
import software.project.mainClasses.Request;
import software.project.mainClasses.RequestHelper;
import software.project.mainClasses.UserHelper;
import software.project.repository.CustomerRepository;
import software.project.repository.TechnicianRepository;
import software.project.repository.UserRepository;
import software.project.repository.RequestRepo;

@Data

@Service
public class TechnicianProfileService {

    @Autowired
    private final TechnicianRepository technicianRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepo;
    private final RequestRepo requestRepo;
    private final PasswordEncoder encoder;

    public ModelAndView viewTechnicianProfile(@AuthenticationPrincipal User user) {

        ModelAndView mav = new ModelAndView("displayTechnician");
        Technician tProfile = user.getTechnicianProfile();
        if (!user.isMailConfrimed()) {
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            mav.addObject("mail", user.getEmail());
            mav.addObject("error", "your email address" + user.getEmail()
                    + " is not confrimed,\n please check your email or resend to confirm");
            mav.setViewName("resendEmail");
            return mav;
        }
        // List<Customer> customerProfiles = new ArrayList<Customer>();
        // List<Request.Status> statusContainer = new ArrayList<Request.Status>();
        // RequestHelper requestHelper = new RequestHelper();
        long sendId = user.getTechnicianProfile().getId();
        List<Request> requestLists = requestRepo.findByTechinician(sendId);

        List<RequestHelper> rHelpers = new ArrayList<RequestHelper>();

        for (Request requests : requestLists) {
            RequestHelper ReqHelp = new RequestHelper();
            customerRepository.findById(requests.getCustomer()).ifPresent(custom -> {
                ReqHelp.setCustomer(custom);

            });

            ReqHelp.setReq(requests);
            ReqHelp.setStatus(requests.getStatus());
            ReqHelp.setUser(user);
            ReqHelp.setTech(user.getTechnicianProfile());
            rHelpers.add(ReqHelp);
        }

        mav.addObject("TechnicianProfile", tProfile);
        mav.addObject("helpList", rHelpers);
        mav.addObject("CurrentUser", user);

        return mav;

    }

    public ModelAndView editTechnicianProfile(@RequestParam Long userId, @RequestParam Long technicianId,
            UserHelper userHelper) {
        ModelAndView mav = new ModelAndView("editTechnicianProfile");
        User techUser = userRepo.findById(userId).get();
        Technician tProfile = technicianRepository.findById(technicianId).get();

        userHelper.setPhotos(techUser.getPhotos());
        userHelper.setFirstName(techUser.getFirstName());
        userHelper.setLastName(techUser.getLastName());
        userHelper.setEmail(techUser.getEmail());
        userHelper.setLocation(techUser.getLocation());
        userHelper.setPhone(techUser.getPhone());
        userHelper.setUsername(techUser.getUsername());
        userHelper.setDevice(tProfile.getDevice());
        userHelper.setDescription(tProfile.getDescription());
        userHelper.setPassword(techUser.getPassword());

        mav.addObject("technicianProfile", userHelper);
        return mav;
    }

    public String saveTutor(@AuthenticationPrincipal User user, UserHelper userHelper, MultipartFile multipartFile) {
        var profile = user.getTechnicianProfile();

        String fileName = org.springframework.util.StringUtils.cleanPath(Filenames.toSimpleFileName(multipartFile.getOriginalFilename()));
        userHelper.setPhotos(fileName);

        profile.setDescription(userHelper.getDescription());
        profile.setDevice(userHelper.getDevice());
        user.setPhotos(userHelper.getPhotos());
        user.setFirstName(userHelper.getFirstName());
        user.setLastName(userHelper.getLastName());
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

        technicianRepository.save(profile);

        return "redirect:/displayTechnicianProfile";
    }

    public String deleteUsers(Long technicianId) {
        userRepo.deleteById(technicianId);
        return "redirect:/login";
    }

}
