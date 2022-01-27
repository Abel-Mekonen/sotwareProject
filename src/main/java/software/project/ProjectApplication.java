package software.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import software.project.mainClasses.User;
import software.project.repository.UserRepository;
// import software.project.mainClasses.User.Gender;
import software.project.mainClasses.User.Role;

@SpringBootApplication

public class ProjectApplication implements CommandLineRunner {
	@Autowired
	UserRepository userRepo;

	@Autowired	
	PasswordEncoder passwordEncoder;

	@Override
	// @Bean
	public void run(String... args) throws Exception {

		if (userRepo.findByRole(Role.ADMIN) == null) {
			User admin = new User();
			admin.setPassword(passwordEncoder.encode("admin"));
			admin.setUsername("admin");
			admin.setFirstName("ADMIN");
			admin.setLastName("ADMIN");
			admin.setEmail("admin12@gmail.com");
			admin.setRole(Role.ADMIN);
			admin.setPhone("0947408989");
			// admin.setGender(Gender.MALE);
			userRepo.save(admin);
		}
		 
	}

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);

	}

}
