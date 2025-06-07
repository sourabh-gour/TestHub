package com.example.testApplication.DTOs;

public class QuestionCreationDTO {
    private Long questionId;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctOption;
    private Long testId;
    public QuestionCreationDTO(Long questionId, String question, String optionA, String optionB, String optionC,
            String optionD, String correctOption, Long testId) {
             this.testId = testId;
             this.questionId = questionId;
             this.question = question;
             this.optionA = optionA;
             this.optionB = optionB;
             this.optionC = optionC;
             this.optionD = optionD;
             this.correctOption = correctOption;
             this.testId = testId;   
            }
    public Long getQuestionId() {
        return questionId;
    }
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getOptionA() {
        return optionA;
    }
    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }
    public String getOptionB() {
        return optionB;
    }
    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }
    public String getOptionC() {
        return optionC;
    }
    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }
    public String getOptionD() {
        return optionD;
    }
    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }
    public String getCorrectOption() {
        return correctOption;
    }
    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }
    public Long getTestId() {
        return testId;
    }
    public void setTestId(Long testId) {
        this.testId = testId;
    }

    
}
