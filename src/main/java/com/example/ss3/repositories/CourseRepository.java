package com.example.ss3.repositories;

import com.example.ss3.models.Course;
import com.example.ss3.models.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("Select c From Course c where c.status = :status")
    public Page<Course> findAllByStatus(
            @Param(value = "status") CourseStatus status,
            Pageable pageable
    );
}
