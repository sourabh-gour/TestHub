package com.example.testApplication.configurations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.testApplication.entities.Question;
import com.example.testApplication.entities.Test;
import com.example.testApplication.repositories.QuestionRepo;
import com.example.testApplication.repositories.TestRepo;

@Component
public class CsvDataLoader {
    @Autowired
    TestRepo testRepo;

    @Autowired
    QuestionRepo questionRepo;

    public void importTests(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        boolean isFirstLine = true;

        while ((line = reader.readLine()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }

            String[] cells = line.split(",");
            Test test = new Test();
            test.setSubject(cells[0]);
            test.setDuration(Integer.parseInt(cells[1]));
            test.setIsActive(Boolean.parseBoolean(cells[2]));
            test.setSchedeledAt(LocalDateTime.parse(cells[3]));
            testRepo.save(test);
        }
    }

    public void importQuestions(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        boolean isFirstLine = true;

        while ((line = reader.readLine()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }

            String[] cells = line.split(",");
            Question question = new Question();
            question.setQuestionText(cells[0]);
            question.setOptionA(cells[1]);
            question.setOptionB(cells[2]);
            question.setOptionC(cells[3]);
            question.setOptionD(cells[4]);
            question.setCorrectOption(cells[5]);
            testRepo.findById(Long.parseLong(cells[6])).ifPresent(question::setTest);

            questionRepo.save(question);
        }
    }
}