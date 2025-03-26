package com.jobregistration.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @Column(name = "job_id")
    private String jobId;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Column(name = "programming_language", nullable = false)
    private String programmingLanguage;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<JobRegistration> jobRegistrations;

    // Constructors
    public Job() {}

    public Job(String jobId, String jobName, String programmingLanguage, String status) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.programmingLanguage = programmingLanguage;
        this.status = status;
    }

    // Getters and Setters
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<JobRegistration> getJobRegistrations() {
        return jobRegistrations;
    }

    public void setJobRegistrations(List<JobRegistration> jobRegistrations) {
        this.jobRegistrations = jobRegistrations;
    }

    // Method to check if job is closed
    public boolean isClosed() {
        return "Closed".equalsIgnoreCase(this.status);
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId='" + jobId + '\'' +
                ", jobName='" + jobName + '\'' +
                ", programmingLanguage='" + programmingLanguage + '\'' +
                ", status='" + status + '\'' +
                ", jobRegistrations=" + jobRegistrations +
                '}';
    }
}