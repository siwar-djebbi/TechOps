package tn.esprit.se.pispring.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.se.pispring.Service.ILeavService;
import tn.esprit.se.pispring.entities.Leav;
import tn.esprit.se.pispring.entities.Notification;
import tn.esprit.se.pispring.entities.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/leav")
public class LeavController {

    private final ILeavService leavService;
    @PostMapping("/addLeav")
    Leav addLeav(@RequestBody Leav leav)
    {
        return leavService.addOrUpdateLeav(leav);
    }
    @PutMapping("/updateLeav")
    Leav updateLeav(@RequestBody Leav leav)
    {
        return leavService.addOrUpdateLeav(leav);
    }
    @GetMapping("/get/{leaveId}")
    Leav getLeav(@PathVariable("leaveId") Long leaveId)
    {
        return leavService.retrieveLeav(leaveId);
    }
    @GetMapping("/allLeavs")
    List<Leav> getAllLeavs() { return leavService.retrieveAllLeaves();}
    @DeleteMapping("/delete/{leaveId}")
    void deleteLeav(@PathVariable("leaveId") Long leaveId)
    {
        leavService.removeLeav(leaveId);
    }

    @PutMapping("/assignLeavToUser/{leaveId}/{id}")
    public ResponseEntity<String> assignLeavToUser(@PathVariable("leaveId") Long leaveId, @PathVariable("id") Long id) {
        try {
            leavService.assignLeavToUser(leaveId, id);
            return ResponseEntity.ok("Leave assigned to user successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to assign leave to user.");
        }
    }

    @PutMapping("/{leaveId}/accept")
    //@PreAuthorize("hasRole('ROLE_HR_ADMIN')")
    public ResponseEntity<String> acceptLeaveRequest(@PathVariable("leaveId") Long leaveId) {
        try {
            leavService.acceptLeaveRequest(leaveId);
            return ResponseEntity.ok("Leave request accepted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to accept leave request: " + e.getMessage());

        }
    }
    @PutMapping("/{leaveId}/refuse")
    public ResponseEntity<String> refuseLeaveRequest(@PathVariable("leaveId") Long leaveId) {
        try {
            leavService.refuseLeaveRequest(leaveId);
            return ResponseEntity.ok("Leave request refused successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to refuse leave request: " + e.getMessage());
        }
    }
    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getNotifications() {
        List<Notification> notifications = leavService.getNotifications();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{leaveId}/userByLeave")
    public ResponseEntity<User> getUserByLeaveId(@PathVariable("leaveId") Long leaveId) {
        User user = leavService.getUserByLeaveId(leaveId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/leaveIdByDate/{leaveStartdate}")
    public ResponseEntity<Long> getLeaveIdByDate(@PathVariable("leaveStartdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date leaveStartdate) {
        System.out.println("Received request to get leave ID for date: " + leaveStartdate);


        Long leaveId = leavService.getLeaveIdByDate(leaveStartdate);

        if (leaveId != null) {
            System.out.println("Leave ID found: " + leaveId);
            return ResponseEntity.ok(leaveId);
        } else {
            System.out.println("Leave ID not found for date: " + leaveStartdate);
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/user/{id}")
    public List<Leav> getLeavesForUser(@PathVariable Long id) {
        return leavService.getLeavesForUser(id);
    }

    @GetMapping("/statistics")
    public Map<String, Long> getLeaveStatistics() {
        return leavService.getLeaveStatistics();
    }
}

