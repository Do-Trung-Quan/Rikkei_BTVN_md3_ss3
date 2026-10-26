package com.example.ss3.repositories;

import com.example.ss3.dto.response.CourseResponseV2;
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

    @Query("Select c From Course c where (:status is null or c.status = :status)")
    public Page<Course> findAllByStatus(
            @Param(value = "status") CourseStatus status,
            Pageable pageable
    );

    @Query("""
            Select new com.example.ss3.dto.response.CourseResponseV2(
                c.id,
                c.title,
                c.status
            )
            from Course c 
            where (:status is null or c.status = :status)
    """)
    public Page<CourseResponseV2> findAllByStatusV2(
            @Param(value = "status") CourseStatus status,
            Pageable pageable
    );
}
