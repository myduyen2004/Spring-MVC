package com.jobregistration.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String gender;

    @Column(name = "employee_level", nullable = false)
    private int employeeLevel;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<JobRegistration> jobRegistrations;

    // Constructors
    public Employee() {}

    public Employee(String employeeId, String fullName, String gender, int employeeLevel) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.gender = gender;
        this.employeeLevel = employeeLevel;
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getEmployeeLevel() {
        return employeeLevel;
    }

    public void setEmployeeLevel(int employeeLevel) {
        this.employeeLevel = employeeLevel;
    }

    public List<JobRegistration> getJobRegistrations() {
        return jobRegistrations;
    }

    public void setJobRegistrations(List<JobRegistration> jobRegistrations) {
        this.jobRegistrations = jobRegistrations;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", employeeLevel=" + employeeLevel +
                ", jobRegistrations=" + jobRegistrations +
                '}';
    }
}