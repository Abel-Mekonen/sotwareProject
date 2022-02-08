package software.project.service;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.springframework.util.StringUtils;

import software.project.mainClasses.ConfrimEmail;
// import antlr.StringUtils;
// import software.project.*;
import software.project.mainClasses.Customer;
import software.project.mainClasses.FileUploadUtil;
import software.project.mainClasses.RegistrationForm;
import software.project.mainClasses.Technician;
import software.project.mainClasses.User;
import software.project.mainClasses.User.Role;
import software.project.repository.ConfirmEmailRepository;
import software.project.repository.CustomerRepository;
import software.project.repository.TechnicianRepository;
import software.project.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
    @Autowired
    private final UserRepository userRepo;
    private final CustomerRepository customerRepository;
    private final TechnicianRepository technicianRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final SendMail sender;
    @Autowired
    private final ConfirmEmailRepository emailRepo;

    public String saveTechnician(RegistrationForm registrationForm, MultipartFile multipartFile) {
        // var user = registrationForm.toUser(passwordEncoder);
        // user.setRole(Role.TECHNICIAN);
        // userRepo.save(user);
        // var profile = new Technician();
        // profile.setUser(user);
        // technicianRepository.save(profile);

        // return "redirect:/login";

        User user = new User();
        String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
        registrationForm.setPhotos(fileName);
        user = registrationForm.toUser(passwordEncoder);
        user.setRole(Role.TECHNICIAN);
        User savedUser = userRepo.save(user);
        var profile = new Technician();
        profile.setUser(user);
        technicianRepository.save(profile);

        String uploadDir = "user-photos/" + savedUser.getId();
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConfrimEmail mail = new ConfrimEmail();
        mail.setUser(savedUser);

        String link = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/confirmEmail?id="
                + mail.getEmailconfirm();
        String contact = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/contactus";
        String confirmEmailText = "<h1>BTHY Electronic Maintenance</h1><br> <p> thanks for reqistering to bthy system </p><br> please click the following link to <a>"
                + link + "</a>";
        sender.sendSimpleMessage(registrationForm.getEmail(), "BTHY Electronics Email Confirmation Link",
                confirmEmailText);
        Context cont = new Context();
        cont.setVariable("confirmLink", link);
        cont.setVariable("contactUs", contact);
        try {
            sender.sendHtmlMail(registrationForm.getEmail(), "BTHY Electronics Email Confirmation Link",
                    "confirmEmailTemplate", cont);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        emailRepo.save(mail);
        return "redirect:/login";
    }

    public String saveCustomer(RegistrationForm registrationForm, MultipartFile multipartFile) {

        User user = new User();
        String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
        registrationForm.setPhotos(fileName);
        user = registrationForm.toUser(passwordEncoder);
        user.setRole(Role.CUSTOMER);
        User savedUser = userRepo.save(user);
        var profile = new Customer();
        profile.setUser(user);
        customerRepository.save(profile);
        String uploadDir = "user-photos/" + savedUser.getId();
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/login";
    }
}
