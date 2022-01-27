package software.project.mainClasses;

import java.sql.Date;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long feedbackId;

    @NotEmpty(message = "Error! content should not be empty")
    private String content;

    @NotEmpty(message = "Error! subject should not be empty")
    private String subject;

    @ManyToOne
    private User sender;

    private Instant sentDate = Instant.now();
    private boolean seen = false;
}
