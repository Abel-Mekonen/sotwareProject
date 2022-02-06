package software.project.repository;

import java.util.List;

// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import software.project.mainClasses.Technician;
import software.project.mainClasses.Technician.Device;
import software.project.mainClasses.User.Location;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {

    List<Technician> searchByDevice(Device device);

    List<Technician> searchByDeviceAndUserLocation(Device device, Location location);

    List<Technician> searchByDeviceAndUserFirstNameContainingOrDeviceAndUserLastNameContaining(Device device,
            String name, Device device2, String name2);

    List<Technician> searchByDeviceAndUserLocationAndUserFirstNameContainingOrDeviceAndUserLocationAndUserLastNameContaining(
            Device device, Location location, String search, Device device2, Location location2, String search2);
}
