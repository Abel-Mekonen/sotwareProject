package software.project.repository;

import java.util.List;

// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import software.project.mainClasses.Technician;
import software.project.mainClasses.Technician.Device;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {

    List<Technician> searchByDevice(Device device);

}

