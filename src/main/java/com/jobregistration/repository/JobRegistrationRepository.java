package com.jobregistration.repository;

import com.jobregistration.model.JobRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRegistrationRepository extends JpaRepository<JobRegistration, Long> {

    @Query("SELECT jr FROM JobRegistration jr JOIN jr.job j WHERE j.status != 'Closed'")
    List<JobRegistration> findAllActiveJobRegistrations();

    @Query("SELECT jr FROM JobRegistration jr JOIN jr.employee e JOIN jr.job j WHERE e.fullName LIKE %:name% AND j.status != 'Closed'")
    List<JobRegistration> findByEmployeeNameContaining(@Param("name") String name);

    @Query("SELECT jr FROM JobRegistration jr JOIN jr.job j WHERE j.jobId = :jobId AND j.status != 'Closed'")
    List<JobRegistration> findByJobId(@Param("jobId") String jobId);

    @Query("SELECT jr FROM JobRegistration jr JOIN jr.employee e JOIN jr.job j WHERE (e.fullName LIKE %:searchTerm% OR j.jobId = :searchTerm) AND j.status != 'Closed'")
    List<JobRegistration> findByEmployeeNameOrJobId(@Param("searchTerm") String searchTerm);
}