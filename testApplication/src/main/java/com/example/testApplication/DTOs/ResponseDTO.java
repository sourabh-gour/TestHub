package com.example.testApplication.DTOs;

public class ResponseDTO {
    private Long questionId;
    private String question;
    private String selectedOption;

    public ResponseDTO(String question, String selectedOption, Long questionId) {
        this.question = question;
        this.selectedOption = selectedOption;
        this.questionId = questionId; 
    }

    public String getQuestion() {
        return question; 
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public Long getQuestionId() {
        return questionId;   
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
