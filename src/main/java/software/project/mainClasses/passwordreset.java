package software.project.mainClasses;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;

// import java.sql.Date;
// import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class passwordreset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private UUID linkParam = UUID.randomUUID();

    private Date resetDate = new Date();

}
