package software.project.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import software.project.mainClasses.ConfrimEmail;
import software.project.mainClasses.User;

public interface ConfirmEmailRepository extends JpaRepository<ConfrimEmail, Long> {

    ConfrimEmail findByEmailconfirm(String emailconfirm);

    ConfrimEmail findByUser(User user);

    ConfrimEmail findByUserEmail(String mail);

}