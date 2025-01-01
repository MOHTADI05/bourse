package tn.esprit.mfb.serviceInterface;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.mfb.entity.Complaint;
import tn.esprit.mfb.entity.ComplaintCategories;
import tn.esprit.mfb.entity.ComplaintStatus;
import tn.esprit.mfb.entity.ComplaintUrgencies;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IComplaintService {
    List<Complaint> selectAllByUserId(Long userId);

    List<Complaint> selectAllByCategory(ComplaintCategories cat);




    void addNewComplaint(Long userIdEntry, Optional<MultipartFile> file, String desc, ComplaintCategories cat, ComplaintUrgencies urgency);

    void answerComplaint(Integer complaintId, String complaintResponse, ComplaintStatus status);

    void cancelComplaint(Integer complaintId);

    void attachFile(Integer complaintId, MultipartFile file);


    List<Complaint> searchByCategory(ComplaintCategories cat);

    List<Complaint> searchByDate(Date q);

    List<Complaint> selectAll();

    Complaint selectById(Integer id);

    Complaint getComplaintById(Integer complaintId);

    void updateComplaintStatusIfAllTasksCompleted(Complaint complaint);
}
