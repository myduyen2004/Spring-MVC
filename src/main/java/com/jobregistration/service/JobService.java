package com.jobregistration.service;

import com.jobregistration.model.Job;
import com.jobregistration.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;


    public Optional<Job> findById(String jobId) {
        return jobRepository.findById(jobId);
    }
    public Job findJobById(String jobId) {
        return jobRepository.findById(jobId).orElse(null);
    }


    public List<Job> findAllActiveJobs() {
        return jobRepository.findByStatusNot("Closed");
    }


    public boolean isJobActiveAndExists(String jobId) {
        Optional<Job> jobOpt = findById(jobId);
        return jobOpt.isPresent() && !jobOpt.get().isClosed();
    }

    public boolean jobExists(String jobId) {
        return jobRepository.existsById(jobId);
    }


    public boolean isJobClosed(String jobId) {
        Optional<Job> jobOpt = findById(jobId);
        return jobOpt.isPresent() && jobOpt.get().isClosed();
    }
}