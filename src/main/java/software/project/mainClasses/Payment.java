package software.project.mainClasses;

// import java.sql.Date;
// import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Payment{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long paymentId;

    private float price;
    private boolean paymentStatus;

    public static enum Payment_Types {
        WIRE,
        MOBILE_BANKING
    }

    private Payment_Types PaymentTypes;
    private String accountInformation;

    @ManyToOne
    private User payer;

    // @ManyToOne
    // private Device device;

}
