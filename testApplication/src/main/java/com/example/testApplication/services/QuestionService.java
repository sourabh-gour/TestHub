package com.example.testApplication.services;

import java.util.List;

import com.example.testApplication.DTOs.QuestionCreationDTO;
import com.example.testApplication.entities.Question;

public interface QuestionService{
    List<Question> getQuestionsByTestId(Long testId);
    Question createQuestion(QuestionCreationDTO questionCreationDTO);
    Question updateQuestion(Long questionId, QuestionCreationDTO questionCreationDTO);
    void deleteQuestion(Long questionId);
}
