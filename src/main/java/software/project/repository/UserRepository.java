package software.project.repository;



import software.project.mainClasses.User;

// import Tutorial.mainClasses.User.Role;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsername(String username);
    // public User findByRole(Role role);
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);


}
