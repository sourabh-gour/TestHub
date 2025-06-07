package com.example.testApplication.DTOs;

import java.time.LocalDateTime;
import java.util.List;

public class TestDTO {
    private Long testId;
    private String subjectTitle;
    private List<QuestionDTO> questions;
    private Integer duration;
    private LocalDateTime scheduledAt;
    private LocalDateTime testStartTime;
    private LocalDateTime endAt;

    public TestDTO(Long testId, String subjectTitle, List<QuestionDTO> questions, Integer duration, LocalDateTime testStartTime, LocalDateTime scheduledAt, LocalDateTime endAt) {
        this.testId = testId;
        this.subjectTitle = subjectTitle;
        this.questions = questions;
        this.duration = duration;
        this.scheduledAt = scheduledAt;
        this.testStartTime = testStartTime;
        this.endAt = endAt;
    }
    public Long getTestId() {
        return testId;
    }
    public void setTestId(Long testId) {
        this.testId = testId;
    }
    public String getSubjectTitle() {
        return subjectTitle;
    }
    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }
    public List<QuestionDTO> getQuestions() {
        return questions;
    }
    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public LocalDateTime getTestStartTime() {
        return testStartTime;
    }
    public void setTestStartTime(LocalDateTime testStartTime) {
        this.testStartTime = testStartTime;
    }
    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }
    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }
    public LocalDateTime getEndAt() {
        return endAt;
    }
    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }
    
    
}
