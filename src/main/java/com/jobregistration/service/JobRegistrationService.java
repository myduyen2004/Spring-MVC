package com.jobregistration.service;

import com.jobregistration.model.Employee;
import com.jobregistration.model.Job;
import com.jobregistration.model.JobRegistration;
import com.jobregistration.repository.JobRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing job registration operations
 * @author YourName
 * @version YYYY_MM_DD
 */
@Service
public class JobRegistrationService {

    @Autowired
    private JobRegistrationRepository jobRegistrationRepository;

    /**
     * Calculate the total amount based on employee level, workdays, and job
     * @author YourName
     * @version YYYY_MM_DD
     * @param employeeLevel The level of the employee
     * @param workdays The number of workdays
     * @param job The job
     * @return The calculated total amount
     */
    public BigDecimal calculateTotalAmount(int employeeLevel, int workdays, Job job) {
        BigDecimal dayRate;

        // Determine daily rate based on employee level
        switch (employeeLevel) {
            case 1:
                dayRate = new BigDecimal("500000");
                break;
            case 2:
                dayRate = new BigDecimal("600000");
                break;
            case 3:
                dayRate = new BigDecimal("700000");
                break;
            default:
                dayRate = BigDecimal.ZERO;
        }

        // Calculate base amount
        BigDecimal totalAmount = dayRate.multiply(new BigDecimal(workdays));

        // Apply 10% bonus for COBOL or RPG languages
        String lang = job.getProgrammingLanguage();
        if ("COBOL".equalsIgnoreCase(lang) || "RPG".equalsIgnoreCase(lang)) {
            BigDecimal bonus = totalAmount.multiply(new BigDecimal("0.1"));
            totalAmount = totalAmount.add(bonus);
        }

        return totalAmount.setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * Save a new job registration
     * @author YourName
     * @version YYYY_MM_DD
     * @param jobRegistration The job registration to save
     * @return The saved job registration
     */
    public JobRegistration save(JobRegistration jobRegistration) {
        return jobRegistrationRepository.save(jobRegistration);
    }

    /**
     * Find all active job registrations (with non-closed jobs)
     * @author YourName
     * @version YYYY_MM_DD
     * @return List of active job registrations
     */
    public List<JobRegistration> findAllActiveJobRegistrations() {
        return jobRegistrationRepository.findAllActiveJobRegistrations();
    }

    /**
     * Find job registrations by employee name
     * @author YourName
     * @version YYYY_MM_DD
     * @param name The employee name to search for
     * @return List of matching job registrations
     */
    public List<JobRegistration> findByEmployeeName(String name) {
        return jobRegistrationRepository.findByEmployeeNameContaining(name);
    }

    /**
     * Find job registrations by job ID
     * @author YourName
     * @version YYYY_MM_DD
     * @param jobId The job ID to search for
     * @return List of matching job registrations
     */
    public List<JobRegistration> findByJobId(String jobId) {
        return jobRegistrationRepository.findByJobId(jobId);
    }

    /**
     * Find job registrations by employee name or job ID
     * @author YourName
     * @version YYYY_MM_DD
     * @param searchTerm The search term to look for
     * @return List of matching job registrations
     */
    public List<JobRegistration> findByEmployeeNameOrJobId(String searchTerm) {
        return jobRegistrationRepository.findByEmployeeNameOrJobId(searchTerm);
    }

    /**
     * Find job registration by ID
     * @author YourName
     * @version YYYY_MM_DD
     * @param id The ID of the job registration
     * @return Optional containing the job registration if found
     */
    public Optional<JobRegistration> findById(Long id) {
        return jobRegistrationRepository.findById(id);
    }
    public JobRegistration findJobRegistrationById(Long id) {
        return jobRegistrationRepository.findById(id).orElse(null);
    }
}