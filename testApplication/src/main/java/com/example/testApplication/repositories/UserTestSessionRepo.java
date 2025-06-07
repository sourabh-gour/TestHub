package com.example.testApplication.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.testApplication.entities.Test;
import com.example.testApplication.entities.User;
import com.example.testApplication.entities.UserTestSession;

@Repository
public interface UserTestSessionRepo extends JpaRepository<UserTestSession, Long>{
    Optional<UserTestSession> findByUserAndTest(User user, Test test);
    List<UserTestSession> findByIsCompletedFalse();
}
