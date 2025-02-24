package com.ismailjacoby.jobtrackerapi.controller;

import com.ismailjacoby.jobtrackerapi.dto.JobDTO;
import com.ismailjacoby.jobtrackerapi.dto.JobShortDTO;
import com.ismailjacoby.jobtrackerapi.entity.JobEntity;
import com.ismailjacoby.jobtrackerapi.exception.NotFoundException;
import com.ismailjacoby.jobtrackerapi.form.JobForm;
import com.ismailjacoby.jobtrackerapi.service.declaration.JobService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public void addJob(@RequestBody @Valid JobForm form){
        jobService.addJob(form);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id){
        JobEntity jobEntity = jobService.getJobById(id)
                .orElseThrow(() -> new NotFoundException("Job not Found"));
        return ResponseEntity.ok(JobDTO.toDto(jobEntity));
    }

    @GetMapping("/all")
    public ResponseEntity<List<JobShortDTO>> getAllJobs(){
        return ResponseEntity.ok(jobService.getAllJobs()
                .stream()
                .map(JobShortDTO::toDto)
                .toList()
        );
    }

    @PutMapping("/{id}")
    public void updateJob(@PathVariable Long id, @RequestBody @Valid JobForm form){
        jobService.updateJob(id, form);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id){
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}
