package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mfb.entity.Complaint;
import tn.esprit.mfb.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByComplaint(Complaint complaint);

}
