package com.ismailjacoby.jobtrackerapi.controller;

import com.ismailjacoby.jobtrackerapi.dto.JobDTO;
import com.ismailjacoby.jobtrackerapi.dto.JobShortDTO;
import com.ismailjacoby.jobtrackerapi.entity.JobEntity;
import com.ismailjacoby.jobtrackerapi.exception.NotFoundException;
import com.ismailjacoby.jobtrackerapi.form.JobForm;
import com.ismailjacoby.jobtrackerapi.service.declaration.JobService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/add")
    public void addJob(@RequestBody @Valid JobForm form, @AuthenticationPrincipal UserDetails user) {
        if (user == null) {
            throw new IllegalArgumentException("User must be authenticated to add a job");
        }

        jobService.addJob(form, user.getUsername());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        JobEntity job = jobService.getJobById(id, user.getUsername());
        return ResponseEntity.ok(JobDTO.toDto(job));
    }

    @GetMapping("/all")
    public ResponseEntity<List<JobShortDTO>> getAllJobs(@AuthenticationPrincipal UserDetails user){
        if (user == null) {
            throw new IllegalArgumentException("User must be authenticated to add a job");
        }

        return ResponseEntity.ok(jobService.getAllJobs(user.getUsername())
                .stream()
                .map(JobShortDTO::toDto)
                .toList()
        );
    }

    @PutMapping("/{id}")
    public void updateJob(@PathVariable Long id, @RequestBody @Valid JobForm form,@AuthenticationPrincipal UserDetails user){
        jobService.updateJob(id, form, user.getUsername());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id, @AuthenticationPrincipal UserDetails user){
        jobService.deleteJob(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }
}
