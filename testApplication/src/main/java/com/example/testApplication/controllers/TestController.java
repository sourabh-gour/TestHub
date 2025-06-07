package com.example.testApplication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.testApplication.DTOs.ResponseDTO;
import com.example.testApplication.configurations.CsvDataLoader;
import com.example.testApplication.serviceImpl.TestServiceImpl;

@RestController
@RequestMapping("/test")
public class TestController {
    TestServiceImpl testServiceImpl;
    CsvDataLoader csvDataLoader;

    @Autowired
    public TestController(TestServiceImpl testServiceImpl, CsvDataLoader csvDataLoader) {
        this.testServiceImpl = testServiceImpl;
        this.csvDataLoader = csvDataLoader;
    }

    @Secured("ROLE_STUDENT")
    @GetMapping("/generate-test/{testId}")
    public ResponseEntity<?> generateTestForStudent(@PathVariable Long testId, @AuthenticationPrincipal UserDetails userDetails) {
       return ResponseEntity.ok(testServiceImpl.generateTestForStudent(testId, userDetails.getUsername())); 
    }

    @Secured("ROLE_STUDENT")
    @GetMapping("/all-upcoming-tests")
    public ResponseEntity<?> getAllUpcomingTests() {
        return ResponseEntity.ok(testServiceImpl.getAllUpcomingTests());
    }

    @Secured("ROLE_STUDENT")
    @PostMapping("/start-test-session/{testId}")
    public ResponseEntity<?> startTestSession(@PathVariable Long testId, @AuthenticationPrincipal UserDetails userDetails) {
        try{
            return ResponseEntity.ok(testServiceImpl.startTestSession(testId, userDetails.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());}
    }

    @Secured("ROLE_STUDENT")
    @PostMapping("/submit-test/{testId}")
    public ResponseEntity<?> submitTest(@PathVariable Long testId,
    @AuthenticationPrincipal UserDetails userDetails, @RequestBody List<ResponseDTO> submittedAnswers) {
        try{
            return ResponseEntity.ok(testServiceImpl.submitTest(testId, userDetails,submittedAnswers));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Secured({"ROLE_STUDENT","ROLE_ADMIN"})
    @GetMapping("/test-result/")
    public ResponseEntity<?> getTestsResultForStudent(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(testServiceImpl.getResultsForUser(userDetails));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/upload-test")
    public ResponseEntity<?> uploadTest(@RequestParam("file") MultipartFile file){
        if(file.isEmpty() || !file.getOriginalFilename().endsWith(".csv")){
            return ResponseEntity.badRequest().body("Invalid file format. Please upload a CSV file.");
        }

        try{
           csvDataLoader.importTests(file.getInputStream());
           return ResponseEntity.ok("Test uploaded successfully."); 
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Error uploading test file: " + e.getMessage());
        }
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/upload-questions")
    public ResponseEntity<?> uploadQuestions(@RequestParam("file") MultipartFile file){
        if(file.isEmpty() ||!file.getOriginalFilename().endsWith(".csv")){
            return ResponseEntity.badRequest().body("Invalid file format. Please upload a CSV file.");
        }

        try{
            csvDataLoader.importQuestions(file.getInputStream());
            return ResponseEntity.ok("Questions uploaded successfully.");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Error uploading questions file: " + e.getMessage());
        }
    }
}
