package software.project.mainClasses;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ch.qos.logback.core.status.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // @OneToMany
    private Long customer;
    // @OneToMany
    private  Long techinician;

    private Status status;

    public static enum Status {
        PENDING,
        ACCEPTED,
        DONE;
        
        private Status() {

        }
    }


}
