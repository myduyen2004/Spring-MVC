package com.jobregistration.service;

import com.jobregistration.model.Employee;
import com.jobregistration.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for managing employee operations
 * @author YourName
 * @version YYYY_MM_DD
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Optional<Employee> findById(String employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

//    public void update(Employee employee) {
//        return employeeRepository.;
//    }
}