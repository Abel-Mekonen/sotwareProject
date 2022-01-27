package software.project.mainClasses;

import org.springframework.beans.factory.annotation.Autowired;

// import com.fasterxml.jackson.core.sym.Name;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import software.project.repository.UserRepository;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {

        
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user != null)
                return user;
        
            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }

    @Autowired
	private SimpleAuthenticationuccessHandler successHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                // .antMatchers("/","/displayTuteeProfile","/editTuteeProfile").hasRole("TUTEE")
                // .antMatchers("/displayTutorProfile","/editTutorProfile","/displayTutorProfile").hasRole("TUTOR")
                // .antMatchers("/adminDashBoard/**").hasRole("ADMIN")       
                .antMatchers("/login").permitAll()
                .and()
                .formLogin().successHandler(successHandler)
                .loginPage("/login")
                .and()
                .logout()
                
                .and()
                .build();
    }
    
}

