package com.example.testApplication.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.testApplication.DTOs.ResponseDTO;
import com.example.testApplication.DTOs.ResultDTO;
import com.example.testApplication.DTOs.TestDTO;
import com.example.testApplication.entities.Question;
import com.example.testApplication.entities.Result;
import com.example.testApplication.entities.Test;
import com.example.testApplication.entities.UserTestSession;

public interface TestService {
    public TestDTO generateTestForStudent(Long testId, String userName);
    public List<Test> getAllUpcomingTests();
    public UserTestSession startTestSession(Long testId, String userName);
    public UserTestSession submitTest(Long testId, UserDetails userDetails, List<ResponseDTO> submittedAnswers);
    List<ResultDTO> getResultsForUser(UserDetails userDetails);
    List<Test> getAllTests();
    List<Question> getAllQuestions();
    List<Test> getAllActiveTests();
    List<Test> getAllInActiveTests();
}
