package com.example.testApplication.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.testApplication.entities.Result;
import com.example.testApplication.entities.Test;
import com.example.testApplication.entities.User;

@Repository
public interface ResultRepo extends JpaRepository<Result, Long>{
    Optional<Result> findByUserAndTest(User user, Test test);
    List<Result> findAllByUser(User user);
}
