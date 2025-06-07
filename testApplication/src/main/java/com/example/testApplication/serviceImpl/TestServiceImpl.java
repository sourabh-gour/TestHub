package com.example.testApplication.serviceImpl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.testApplication.DTOs.QuestionDTO;
import com.example.testApplication.DTOs.ResponseDTO;
import com.example.testApplication.DTOs.ResultDTO;
import com.example.testApplication.DTOs.TestDTO;
import com.example.testApplication.entities.Question;
import com.example.testApplication.entities.Response;
import com.example.testApplication.entities.Result;
import com.example.testApplication.entities.Test;
import com.example.testApplication.entities.TestStatus;
import com.example.testApplication.entities.User;
import com.example.testApplication.entities.UserTestSession;
import com.example.testApplication.repositories.QuestionRepo;
import com.example.testApplication.repositories.ResponseRepo;
import com.example.testApplication.repositories.ResultRepo;
import com.example.testApplication.repositories.TestRepo;
import com.example.testApplication.repositories.UserRepo;
import com.example.testApplication.repositories.UserTestSessionRepo;
import com.example.testApplication.services.TestService;

@Service
public class TestServiceImpl implements TestService{
    
    TestRepo testRepo;
    QuestionRepo questionRepo;
    UserRepo userRepo;
    UserTestSessionRepo userTestSessionRepo;
    ResponseRepo responseRepo;
    ResultRepo resultRepo;

    @Autowired
    public TestServiceImpl(TestRepo testRepo, QuestionRepo questionRepo, UserRepo userRepo, UserTestSessionRepo userTestSessionRepo, ResponseRepo responseRepo, ResultRepo resultRepo) {
        this.testRepo = testRepo;
        this.questionRepo = questionRepo;
        this.userRepo = userRepo; 
        this.userTestSessionRepo = userTestSessionRepo;
        this.responseRepo = responseRepo;
        this.resultRepo = resultRepo;
    }

    @Override
    public TestDTO generateTestForStudent(Long testId, String userName) {
        
        Optional<User> userOptional = userRepo.findByUserName(userName);
        if(userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        Optional<Test> testOptional = testRepo.findById(testId);
        if(testOptional.isEmpty()){
            throw new RuntimeException("Test not found.");
        }
        Test test = testOptional.get();
        
        List<Question> questions = questionRepo.findByTest_TestId(testId);

        List<QuestionDTO> questionDTOs = questions.stream().map(question ->{
            List<String> options = Arrays.asList(
                question.getOptionA(),
                question.getOptionB(),
                question.getOptionC(),
                question.getOptionD()
            );
            Collections.shuffle(options);
            return new QuestionDTO(question.getQuestionId(), question.getQuestionText(), options);
        }).collect(Collectors.toList());

        Collections.shuffle(questionDTOs);

        return new TestDTO(testId, test.getSubject(), questionDTOs, test.getDuration(), LocalDateTime.now(),test.getScheduledAt(), test.getScheduledAt().plusMinutes(test.getDuration()));
    }

    @Override
    public List<Test> getAllUpcomingTests(){
        return testRepo.findAllUpcomingTests();
    }

    @Override
    public UserTestSession startTestSession(Long testId, String userName) {
        User user = userRepo.findByUserName(userName).orElseThrow(() -> new RuntimeException("User not found"));
        Test test = testRepo.findById(testId).orElseThrow(() -> new RuntimeException("Test not found"));

        Optional<UserTestSession> existingSession = userTestSessionRepo.findByUserAndTest(user, test);

        if(existingSession.isPresent() && !existingSession.get().getIsCompleted()){
            throw new RuntimeException("Test session already exists");
        }

        UserTestSession session = new UserTestSession();
        session.setUser(user);
        session.setTest(test);
        session.setStartTime(LocalDateTime.now());
        return userTestSessionRepo.save(session);
    }
    
    // @Scheduled(fixedRate = 60000)
    public void autoSubmitTests(){
        List<UserTestSession> activeSessions = userTestSessionRepo.findByIsCompletedFalse();

        for(UserTestSession session : activeSessions){
            LocalDateTime expectedEndTime = session.getStartTime().plusMinutes(session.getTest().getDuration());

            if(LocalDateTime.now().isAfter(expectedEndTime)){
                session.setEndTime(expectedEndTime);
                session.setIsCompleted(true);
                userTestSessionRepo.save(session);
            }
        }
    }

    @Override
    public UserTestSession submitTest(Long testId, UserDetails userDetails, List<ResponseDTO> submittedAnswers) {
        Test test = testRepo.findById(testId).orElseThrow(() -> new RuntimeException("Test not found"));

        User user = userRepo.findByUserName(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

        UserTestSession session = userTestSessionRepo.findByUserAndTest(user, test).orElseThrow(() -> new RuntimeException("Test session not found"));

        if(session.getIsCompleted()){
            throw new RuntimeException("Test session already completed");
        }

        int score = 0;

        for(ResponseDTO responseDTO : submittedAnswers){
           Question question = questionRepo.findById(responseDTO.getQuestionId()).orElseThrow(() -> new RuntimeException("Question not found"));
           
           if(question.getCorrectOption().equals(responseDTO.getSelectedOption())){
            score++;
           }

           Response response = new Response();
           response.setUser(user);
           response.setTest(test);
           response.setQuestion(question);
           response.setSelectedOption(responseDTO.getSelectedOption());

           responseRepo.save(response);
        }
        session.setEndTime(LocalDateTime.now());
        session.setIsCompleted(true);
        userTestSessionRepo.save(session);

        Result result =new Result();
        result.setUser(user);
        result.setTest(test);
        result.setScore(score);
        result.setTimeTaken(Duration.between(session.getStartTime(), session.getEndTime()).toMinutes());
        result.setTestStatus((score>= (int)Math.ceil(questionRepo.countByTest_TestId(testId) * 0.4)) ? TestStatus.PASSED : TestStatus.FAILED);
        resultRepo.save(result);
        return session;
    }

    @Override
    public List<ResultDTO> getResultsForUser(UserDetails userDetails) {
        User user = userRepo.findByUserName(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Result> results = resultRepo.findAllByUser(user);

        return results.stream().map(result -> {
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setSubjectTitle(result.getTest().getSubject());
            resultDTO.setCorrectAnswers(result.getScore());
            resultDTO.setTotalQuestions(questionRepo.countByTest_TestId(result.getTest().getTestId()));
            resultDTO.setTestStatus(result.getTestStatus());
            return resultDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Test> getAllTests() {
        return testRepo.findAll();
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepo.findAll();
    }

    @Override
    public List<Test> getAllActiveTests() {
        return testRepo.findAllActiveTests();
    }

    @Override
    public List<Test> getAllInActiveTests() {
        return testRepo.findAllInActiveTests();
    }

    
    
}