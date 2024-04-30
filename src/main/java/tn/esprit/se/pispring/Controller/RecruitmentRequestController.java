package tn.esprit.se.pispring.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.se.pispring.Service.IRecruitmentRequestService;
import tn.esprit.se.pispring.entities.RecruitmentRequest;
import tn.esprit.se.pispring.secConfig.FileUploadConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/recruitmentrequest")
public class RecruitmentRequestController {
    private final IRecruitmentRequestService recruitmentRequestService;
    private final FileUploadConfig fileUploadConfig;

    @PostMapping("/createrequest")
    RecruitmentRequest createRecruitmentRequest(@RequestBody RecruitmentRequest recruitmentRequest)
    {
        return recruitmentRequestService.addRecruitmentrequest(recruitmentRequest);
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
        }

        try {
            // Save the file to the upload directory
            byte[] bytes = file.getBytes();
            Path path = Paths.get(fileUploadConfig.getUploadDir() + File.separator + file.getOriginalFilename());
            Files.write(path, bytes);

            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }
}

