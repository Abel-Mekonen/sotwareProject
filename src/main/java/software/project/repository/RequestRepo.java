package software.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import software.project.mainClasses.Request;

public interface RequestRepo extends CrudRepository<Request, Long>{
    // @Query(value = "select tutee from request where tutor = :tutorId", nativeQuery = true)
    // Optional<List<Long>> search(@Param("tutorId") Long tutorId);

}
