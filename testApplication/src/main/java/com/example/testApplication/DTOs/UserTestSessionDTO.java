package com.example.testApplication.DTOs;

import java.time.LocalDateTime;

public class UserTestSessionDTO {
    private Long userTestSessionId;
    private Long userId;
    private Long testId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isCompleted;
    
    public UserTestSessionDTO(Long userTestSessionId, Long userId, Long testId, LocalDateTime startTime,
            LocalDateTime endTime, Boolean isCompleted) {
        this.userTestSessionId = userTestSessionId;
        this.userId = userId;
        this.testId = testId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCompleted = isCompleted;
    }

    public Long getUserTestSessionId() {
        return userTestSessionId;
    }

    public void setUserTestSessionId(Long userTestSessionId) {
        this.userTestSessionId = userTestSessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    
}
