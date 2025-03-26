package com.jobregistration.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "job_registrations")
public class JobRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Column(name = "workdays", nullable = false)
    private int workdays;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    // Constructors
    public JobRegistration() {}

    public JobRegistration(Employee employee, Job job, int workdays, BigDecimal totalAmount) {
        this.employee = employee;
        this.job = job;
        this.workdays = workdays;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public int getWorkdays() {
        return workdays;
    }

    public void setWorkdays(int workdays) {
        this.workdays = workdays;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "JobRegistration{" +
                "id=" + id +
                ", employee=" + employee.toString() +
                ", job=" + job.toString() +
                ", workdays=" + workdays +
                ", totalAmount=" + totalAmount +
                '}';
    }
}