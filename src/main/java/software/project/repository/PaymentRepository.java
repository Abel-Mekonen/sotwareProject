package software.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import software.project.mainClasses.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "select * from payment where request_id = :id", nativeQuery = true)
    Optional<Payment> findRequest(long id);

    List<Payment> findByPayerIdOrderBySentDateDesc(long id);
}
