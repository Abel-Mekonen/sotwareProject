package software.project.repository;

import java.util.List;

// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import software.project.mainClasses.Technician;
// import Tutorial.mainClasses.TutorProfile.Course;
// import Tutorial.mainClasses.TutorProfile.Level;
import software.project.mainClasses.Technician.Device;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {
    // public Optional<TutorProfile> findById(Long Id);
    List<Technician> searchByDevice(Device device);

}
