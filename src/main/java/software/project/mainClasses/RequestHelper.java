package software.project.mainClasses;



import org.springframework.stereotype.Component;
import software.project.mainClasses.Request.Status;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class RequestHelper {
    
    
    private Customer customer;
    private Technician tech;
    private Request req;
    private User user;
    private Status status;
    private Payment payment;


}
