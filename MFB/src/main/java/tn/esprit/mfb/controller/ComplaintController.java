package tn.esprit.mfb.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.mfb.Services.ComplaintService;
import tn.esprit.mfb.entity.Complaint;
import tn.esprit.mfb.entity.ComplaintCategories;
import tn.esprit.mfb.entity.ComplaintStatus;
import tn.esprit.mfb.entity.ComplaintUrgencies;
import tn.esprit.mfb.exeption.ComplaintServiceException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;

    @Autowired
    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @GetMapping("/allComplaints")
    public List<Complaint> getAllComplaints() {
        return complaintService.selectAll();
    }

    @GetMapping("/allComplaints/cinsearch/{cin}")
    public List<Complaint> getAllComplaintsByUserId(@PathVariable("cin") Long id) {
        return complaintService.selectAllByUserId(id);
    }

    @GetMapping("/allComplaints/catsearch/{complaintCategory}")
    public List<Complaint> getAllComplaintsByCategory(@PathVariable("complaintCategory") ComplaintCategories cat) {
        return complaintService.selectAllByCategory(cat);
    }

    @GetMapping("/complaint/{complaintId}")
    public Complaint getComplaintById(@PathVariable("complaintId") int id) {
        return complaintService.selectById(id);
    }

    @PostMapping("/newComplaint/{userId}")
    public ResponseEntity<?> addNewComplaint(@PathVariable Long userId,
                                             @RequestParam(required = false) MultipartFile file,
                                             @RequestBody Map<String, String> request) {
        try {
            String desc = request.get("desc");
            String cat = request.get("cat");
            String urgency = request.get("urgency");

            ComplaintCategories category = ComplaintCategories.valueOf(cat);
            ComplaintUrgencies urgencyEnum = ComplaintUrgencies.valueOf(urgency);

            complaintService.addNewComplaint(userId, Optional.ofNullable(file), desc, category, urgencyEnum);
            System.out.println(desc);
            System.out.println(userId);
            System.out.println(category);
            System.out.println(urgencyEnum);



            return ResponseEntity.ok("Complaint added successfully.");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid user ID format.");
        } catch (ComplaintServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add complaint: " + e.getMessage());
        }
    }

    @PutMapping("/answear/{complaintId}")
    public void answerComplaint(@PathVariable("complaintId") Integer complaintId, @RequestParam String complaintResponse, @RequestParam ComplaintStatus status) {
        complaintService.answerComplaint(complaintId, complaintResponse, status);
    }

    @PutMapping("/cancel/{complaintId}")
    public void cancelComplaint(@PathVariable("complaintId") Integer complaintId) {
        complaintService.cancelComplaint(complaintId);
    }

    @PutMapping("/fileAttachement/{complaintId}")
    public void attachFileToComplaint(@PathVariable("complaintId") Integer complaintId, MultipartFile file) {
        complaintService.attachFile(complaintId, file);
    }

    @GetMapping("/cat-search")
    public List<Complaint> searchByCategory(@RequestParam("complaintCategory") ComplaintCategories cat) {
        return complaintService.searchByCategory(cat);
    }

    @GetMapping("/date-search")
    public List<Complaint> searchByDate(@RequestParam("complaintDate") Date d) {
        return complaintService.searchByDate(d);
    }
}
