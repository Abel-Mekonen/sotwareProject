package software.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import software.project.mainClasses.Request;

public interface RequestRepo extends CrudRepository<Request, Long>{
    @Query(value = "select customer from request where techinician = :technicianId", nativeQuery = true)
    Optional<List<Long>> search(@Param("technicianId") Long technicianId);


    @Query(value = "select sent_date from request where techinician = :technicianId", nativeQuery = true)
    Optional<List<Long>> searchdate(@Param("technicianId") Long technicianId);
}
