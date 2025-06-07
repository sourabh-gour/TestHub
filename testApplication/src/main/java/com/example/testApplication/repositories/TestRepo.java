package com.example.testApplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.testApplication.entities.Test;
@Repository
public interface TestRepo extends JpaRepository<Test, Long>{
    Test findByTestId(Long testId);

    @Query("SELECT t FROM Test t WHERE t.scheduledAt > CURRENT_TIMESTAMP and t.isActive = true ORDER BY t.scheduledAt ASC")
    List<Test> findAllUpcomingTests();

    @Query("SELECT t FROM Test t WHERE t.isActive = true")
    List<Test> findAllActiveTests();

    @Query("SELECT t FROM Test t WHERE t.isActive = false")
    List<Test> findAllInActiveTests();
}
