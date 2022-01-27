package software.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import software.project.mainClasses.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long>{

    List<Feedback> findBySeen(boolean b);
}
