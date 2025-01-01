package tn.esprit.mfb.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.mfb.Repository.ComplaintRepository;
import tn.esprit.mfb.Repository.TaskRepository;
import tn.esprit.mfb.Repository.UserRepository;
import tn.esprit.mfb.entity.*;
import tn.esprit.mfb.exeption.ComplaintNotFoundException;
import tn.esprit.mfb.exeption.ComplaintServiceException;
import tn.esprit.mfb.serviceInterface.IComplaintService;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService implements IComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    private final TaskRepository taskRepository;

    @Autowired
    public ComplaintService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Autowired
    private NotificationService notificationService;

    @Override
    public List<Complaint> selectAll() {
        try {
            return complaintRepository.findAll();
        } catch (Exception e) {
            throw new ComplaintServiceException("Error while retrieving all complaints", e);
        }
    }

    @Override
    public Complaint selectById(Integer id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new ComplaintNotFoundException("Complaint not found with id: " + id));
    }

    @Override
    public List<Complaint> selectAllByUserId(Long userId) {
        return complaintRepository.findAllByUsercCin(userId);
    }

    @Override
    public List<Complaint> selectAllByCategory(ComplaintCategories cat) {
        return complaintRepository.findAllByComplaintCategory(cat);
    }

    @Override
    public void addNewComplaint(Long userIdEntry, Optional<MultipartFile> file, String desc, ComplaintCategories cat, ComplaintUrgencies urgency) {
        User user = userRepository.findById(userIdEntry)

                .orElseThrow(() -> new ComplaintServiceException("User not found with ID: " + userIdEntry));
        System.out.println(userIdEntry);

        Complaint cl = new Complaint();
        cl.setUserc(user);
        System.out.println(cl);

        cl.setComplaintUrgency(urgency);

        if (file.isPresent()) {
            processFile(cl, file.get());
        }

        cl.setComplaintCategory(cat);
        cl.setComplaintDescription(desc);
        cl.setComplaintStatus(ComplaintStatus.CREATED);
        cl.setComplaintDate(new Date());
        System.out.println(cl);
        System.out.println(cat);

        complaintRepository.save(cl);

        System.out.println(cl);
        System.out.println(desc);
        System.out.println(user.getCin());






        createNotification(user, cl,"New Complaint", "A new complaint has been registered");
        complaintRepository.save(cl);
    }

    private void processFile(Complaint complaint, MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty()) {
                throw new ComplaintServiceException("File name is null or empty");
            }
            String fileName = StringUtils.cleanPath(originalFilename);
            if (fileName.contains("..")) {
                throw new ComplaintServiceException("Invalid file name: " + fileName);
            }
            complaint.setAttachComplaint(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new ComplaintServiceException("Error while processing file: " + e.getMessage(), e);
        }
    }

    private void saveComplaint(Complaint complaint) {
        complaintRepository.save(complaint);
    }

    private void createNotification(User user,Complaint cl ,String subject, String content) {
        Notification notification = new Notification();
        notification.setNotificationSubject(subject);
        notification.setNotificationContent(content);
        notification.setComplaintCategory(cl.getComplaintCategory());
        notification.setUser(user);
        notification.setNotificationDestin(1L);
        notification.setComplaint(cl);
        notification.setNotificationDate(new Date());
        notificationService.createNotification(notification);
    }
    @Override
    public void answerComplaint(Integer complaintId, String complaintResponse, ComplaintStatus status) {
        Complaint existingComplaint = getComplaintById(complaintId);
        existingComplaint.setComplaintResponse(complaintResponse);
        existingComplaint.setComplaintStatus(status);
        existingComplaint.setComplaintResponseDate(new Date());
        saveComplaint(existingComplaint);
        createNotification(existingComplaint.getUserc(),existingComplaint, "Complaint Answered", "Your complaint has been answered.");
    }

    @Override
    public void cancelComplaint(Integer cId) {
        Complaint existingComplaint = getComplaintById(cId);
        existingComplaint.setComplaintStatus(ComplaintStatus.CANCELLED);
        existingComplaint.setComplaintResponseDate(new Date());
        saveComplaint(existingComplaint);
        createNotification(existingComplaint.getUserc(), existingComplaint,"Complaint Cancelled", "Your complaint has been cancelled.");
    }

    @Override
    public void attachFile(Integer complaintId, MultipartFile file) {
        Complaint complaint = getComplaintById(complaintId);
        processFile(complaint, file);
        saveComplaint(complaint);
    }

    @Override
    public List<Complaint> searchByCategory(ComplaintCategories cat) {
        return complaintRepository.findByComplaintCategory(cat);
    }

    @Override
    public List<Complaint> searchByDate(Date q) {
        return complaintRepository.findByComplaintDate(q);
    }
    @Override
    public Complaint getComplaintById(Integer complaintId) {
        return complaintRepository.findById(complaintId)
                .orElseThrow(() -> new ComplaintNotFoundException("Complaint not found with ID: " + complaintId));
    }
    @Override

    public void updateComplaintStatusIfAllTasksCompleted(Complaint complaint) {
        List<Task> tasks = taskRepository.findByComplaint(complaint);
        boolean allCompleted = tasks.stream()
                .allMatch(task -> task.getStatus() == TaskStatus.COMPLETED);
        if (allCompleted) {
            complaint.setComplaintStatus(ComplaintStatus.VERIFIED);
            complaintRepository.save(complaint);
        }
    }
}
