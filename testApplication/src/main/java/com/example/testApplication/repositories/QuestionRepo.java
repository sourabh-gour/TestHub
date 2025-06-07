package com.example.testApplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.testApplication.entities.Question;

@Repository
@EnableJpaRepositories
public interface QuestionRepo extends JpaRepository<Question, Long>{
    List<Question> findByTest_TestId(Long testId);
    Integer countByTest_TestId(Long testId);
}
