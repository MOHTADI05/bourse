
package tn.esprit.mfb.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mfb.entity.Complaint;
import tn.esprit.mfb.entity.ComplaintCategories;

import java.util.Date;
import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
    List<Complaint> findByComplaintCategory(ComplaintCategories cat);

    List<Complaint> findByComplaintDate(Date q);

    List<Complaint> findAllByComplaintCategory(ComplaintCategories cat);

    List<Complaint> findAllByUsercCin(Long cin);


    int countTasksByComplaintId(Integer complaintId);

    int countEffortByComplaintId(Integer complaintId);

//    void updateProgress(Integer complaintId, int complaintProgress);
}