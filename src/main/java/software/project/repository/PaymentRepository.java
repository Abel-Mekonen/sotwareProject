package software.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import software.project.mainClasses.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
}
