package software.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import software.project.mainClasses.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "select * from customer_profile c where id = :id", nativeQuery = true)
    Customer searchfor(@Param("id") Long id );
}
