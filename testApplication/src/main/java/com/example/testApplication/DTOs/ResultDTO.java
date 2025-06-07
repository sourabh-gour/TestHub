package com.example.testApplication.DTOs;

import java.time.LocalDateTime;

import com.example.testApplication.entities.TestStatus;

public class ResultDTO {
    private String subjectTitle;
    private int totalQuestions;
    private int correctAnswers;
    private TestStatus testStatus;
    private LocalDateTime submittedAt;

    public ResultDTO() {
        
    }

    public ResultDTO(String subjectTitle, int totalQuestions, int correctAnswers, TestStatus testStatus, LocalDateTime submittedAt) {
        this.subjectTitle = subjectTitle;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.testStatus = testStatus;
        this.submittedAt = submittedAt;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public TestStatus getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(TestStatus testStatus) {
        this.testStatus = testStatus;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    
}
