package software.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import software.project.mainClasses.User;
import software.project.repository.UserRepository;

@Component
public class LoggedUser{

    @Autowired
    private UserRepository userRepo;
    
    public String get_Username(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;

        if (principal instanceof UserDetails) {
		    return ((UserDetails)principal).getUsername();

        }
		return null;
    }

    public User get_User(){
        String username = get_Username();
        if(username != null){
            return userRepo.findByUsername(username);
        }
        return null;
    }
}