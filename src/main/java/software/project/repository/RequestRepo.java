package software.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import software.project.mainClasses.Request;

public interface RequestRepo extends CrudRepository<Request, Long>{
    // @Query(value = "select * from request where techinician = :technicianId", nativeQuery = true)
    // Optional<List<Long>> search(@Param("technicianId") Long technicianId);

    List<Request> findByTechinician(Long techinician);


    // @Query(value = "select status from request where customer = :customerId", nativeQuery = true)
    // Request searchStatus(@Param("technicianId") Long customerId);
    List<Request> findByCustomer(Long customer);


    @Query(value = "select sent_date from request where techinician = :technicianId", nativeQuery = true)
    Optional<List<Long>> searchdate(@Param("technicianId") Long technicianId);
}
