package software.project.mainClasses;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private Long customer;
    private  Long techinician;

    private Status status;

    public static enum Status {
        PENDING,
        ACCEPTED,
        DECLINE,
        DONE;
        
        private Status() {

        }
    }
    private Date sentDate = new Date();

//     SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
// System.out.println(formatter.format(date));

}
