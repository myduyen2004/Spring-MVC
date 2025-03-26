package com.jobregistration.controller;

import com.jobregistration.model.Employee;
import com.jobregistration.model.Job;
import com.jobregistration.model.JobRegistration;
import com.jobregistration.service.EmployeeService;
import com.jobregistration.service.JobRegistrationService;
import com.jobregistration.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/registration")
public class JobRegistrationController {

    @Autowired
    private JobRegistrationService jobRegistrationService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JobService jobService;


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("jobRegistration", new JobRegistration());
        model.addAttribute("employee", new Employee());
        return "create-registration";
    }
@PostMapping("/create")
public String createJobRegistration(@ModelAttribute JobRegistration jobRegistration,
                                    BindingResult result,
                                    Model model) {
    // Validate dữ liệu
    if (result.hasErrors()) {
        return "create-registration";
    }

    employeeService.save(jobRegistration.getEmployee());

    // Kiểm tra công việc có tồn tại không
    Optional<Job> existingJob = jobService.findById(jobRegistration.getJob().getJobId());
    if (existingJob.isEmpty()) {
        model.addAttribute("error", "Mã công việc không tồn tại!");
        return "create-registration";
    }
    if(jobService.isJobClosed(jobRegistration.getJob().getJobId())) {
        model.addAttribute("error", "Mã công việc không được mở");
        return "create-registration";
    }

    Job job = existingJob.get();
    // Tính tổng tiền dựa trên cấp bậc & số ngày công
    BigDecimal totalAmount = jobRegistrationService.calculateTotalAmount(jobRegistration.getEmployee().getEmployeeLevel(), jobRegistration.getWorkdays(), job);
    jobRegistration.setTotalAmount(totalAmount);

    // Lưu vào database
    jobRegistrationService.save(jobRegistration);

    // Trả về form với thông báo
    model.addAttribute("success", "Đăng ký thành công!");
    model.addAttribute("amount", totalAmount);
    return "create-registration";
}

    @GetMapping("/list")
    public String listRegistrations(Model model) {
        List<JobRegistration> registrations = jobRegistrationService.findAllActiveJobRegistrations();
        model.addAttribute("registrations", registrations);
        return "list-registrations";
    }
    @GetMapping("/search")
    public String searchRegistrations(
            @RequestParam("searchType") String searchType,
            @RequestParam("searchTerm") String searchTerm,
            Model model) {

        List<JobRegistration> registrations;

        if ("name".equals(searchType)) {
            registrations = jobRegistrationService.findByEmployeeName(searchTerm);
        } else if ("jobId".equals(searchType)) {
            registrations = jobRegistrationService.findByJobId(searchTerm);
        } else {
            registrations = jobRegistrationService.findByEmployeeNameOrJobId(searchTerm);
        }

        model.addAttribute("registrations", registrations);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("searchType", searchType);
        return "list-registrations";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<JobRegistration> jobRegistrationOpt = jobRegistrationService.findById(id);

        if (jobRegistrationOpt.isPresent()) {
            JobRegistration jobRegistration = jobRegistrationOpt.get();
            model.addAttribute("jobRegistration", jobRegistration);
            return "update-registrations";
        } else {
            return "redirect:/registration/list";
        }
    }
    @PostMapping("/update/{id}")
    public String updateJobRegistration(@PathVariable Long id, @ModelAttribute JobRegistration jobRegistration, Model model) {
        JobRegistration existingRegistration = jobRegistrationService.findJobRegistrationById(id);
        if (existingRegistration == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Registration không tồn tại!");
        }

        // Validate số ngày công
        if (jobRegistration.getWorkdays() > existingRegistration.getWorkdays()) {
            String errorMessage = "Error_" + "20040331" + ": So ngay cong thay doi khong hop le";
            model.addAttribute("errorMessage", errorMessage);
            return "update-registrations";
        }

        // Kiểm tra mã việc làm có tồn tại không
        Job job = jobService.findJobById(jobRegistration.getJob().getJobId());
        if (job == null) {
            model.addAttribute("errorMessage", "Khong ton tai viec lam trong he thong");
            return "update-registrations";
        }

        // Kiểm tra trạng thái việc làm
        if ("Closed".equalsIgnoreCase(job.getStatus())) {
            model.addAttribute("errorMessage", "Viec lam dang chon da duoc dong");
            return "update-registrations";
        }

        existingRegistration.setWorkdays(jobRegistration.getWorkdays());
        existingRegistration.getEmployee().setFullName(jobRegistration.getEmployee().getFullName());
        existingRegistration.getEmployee().setGender(jobRegistration.getEmployee().getGender());
        existingRegistration.getEmployee().setEmployeeLevel(jobRegistration.getEmployee().getEmployeeLevel());

        BigDecimal totalAmount = jobRegistrationService.calculateTotalAmount(existingRegistration.getEmployee().getEmployeeLevel(), existingRegistration.getWorkdays(), job);
        existingRegistration.setTotalAmount(totalAmount);

        jobRegistrationService.save(existingRegistration);
        model.addAttribute("totalAmount", existingRegistration.getTotalAmount());

        return "update-registrations";
    }


}