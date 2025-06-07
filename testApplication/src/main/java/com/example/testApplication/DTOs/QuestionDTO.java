package com.example.testApplication.DTOs;

import java.util.List;

public class QuestionDTO {
    private Long questionId;;
    private String questionText;
    private List<String> options;
    
    public QuestionDTO(Long questionId, String questionText, List<String> options) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.options = options;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    
}
