package com.ismailjacoby.jobtrackerapi.controller;

import com.ismailjacoby.jobtrackerapi.dto.JobDTO;
import com.ismailjacoby.jobtrackerapi.dto.JobShortDTO;
import com.ismailjacoby.jobtrackerapi.entity.JobEntity;
import com.ismailjacoby.jobtrackerapi.exception.NotFoundException;
import com.ismailjacoby.jobtrackerapi.form.JobForm;
import com.ismailjacoby.jobtrackerapi.service.declaration.JobService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * Creates a new job.
     *
     * @param form The job data.
     * @param user The authenticated user.
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public void addJob(@RequestBody @Valid JobForm form, @AuthenticationPrincipal UserDetails user) {
        jobService.addJob(form, user.getUsername());
    }

    /**
     * Retrieves a job by ID.
     *
     * @param id   The job ID.
     * @param user The authenticated user.
     * @return The job details.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        JobEntity job = jobService.getJobById(id, user.getUsername());
        return ResponseEntity.ok(JobDTO.toDto(job));
    }

    /**
     * Retrieves all jobs for the authenticated user.
     *
     * @param user The authenticated user.
     * @return A list of jobs.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/all")
    public ResponseEntity<List<JobShortDTO>> getAllJobs(@AuthenticationPrincipal UserDetails user){
        return ResponseEntity.ok(jobService.getAllJobs(user.getUsername())
                .stream()
                .map(JobShortDTO::toDto)
                .toList()
        );
    }

    /**
     * Updates a job.
     *
     * @param id   The job ID.
     * @param form The updated job data.
     * @param user The authenticated user.
     */
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public void updateJob(@PathVariable Long id, @RequestBody @Valid JobForm form,@AuthenticationPrincipal UserDetails user){
        jobService.updateJob(id, form, user.getUsername());
    }

    /**
     * Deletes a job.
     *
     * @param id   The job ID.
     * @param user The authenticated user.
     * @return HTTP 204 No Content on success.
     */
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id, @AuthenticationPrincipal UserDetails user){
        jobService.deleteJob(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }
}
