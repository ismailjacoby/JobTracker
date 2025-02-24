package com.ismailjacoby.jobtrackerapi.service.implementation;

import com.ismailjacoby.jobtrackerapi.dto.JobDTO;
import com.ismailjacoby.jobtrackerapi.dto.JobShortDTO;
import com.ismailjacoby.jobtrackerapi.entity.JobEntity;
import com.ismailjacoby.jobtrackerapi.enums.JobStatus;
import com.ismailjacoby.jobtrackerapi.exception.NotFoundException;
import com.ismailjacoby.jobtrackerapi.form.JobForm;
import com.ismailjacoby.jobtrackerapi.repository.JobRepository;
import com.ismailjacoby.jobtrackerapi.service.declaration.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public void addJob(JobForm form) {
        if(form == null) {
            throw new IllegalArgumentException("Form cannot be null");
        }

        JobEntity job = new JobEntity();
        job.setCompany(form.company());
        job.setTitle(form.title());
        job.setLocation(form.location());
        job.setStatus(form.status());
        job.setDateApplied(form.dateApplied());
        job.setLink(form.link());
        job.setDescription(form.description());
        job.setSalary(form.salary());
        job.setNotes(form.notes());
        jobRepository.save(job);
    }

    @Override
    public Optional<JobEntity> getJobById(Long id) {
        Optional<JobEntity> job = jobRepository.findById(id);

        if(job.isPresent()) {
            return job;
        } else {
            throw new NotFoundException("Job not found");
        }
    }

    @Override
    public List<JobEntity> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public void updateJob(Long id, JobForm form) {
        if(form == null) {
            throw new IllegalArgumentException("Form cannot be null");
        }

        JobEntity job = jobRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Job not found."));

        job.setCompany(form.company());
        job.setTitle(form.title());
        job.setLocation(form.location());
        job.setStatus(form.status());
        job.setDateApplied(form.dateApplied());
        job.setLink(form.link());
        job.setDescription(form.description());
        job.setSalary(form.salary());
        job.setNotes(form.notes());
        jobRepository.save(job);
    }

    @Override
    public void deleteJob(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new NotFoundException("Job not found.");
        }
        jobRepository.deleteById(id);
    }
}
