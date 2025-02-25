package com.ismailjacoby.jobtrackerapi.service.implementation;

import com.ismailjacoby.jobtrackerapi.dto.JobDTO;
import com.ismailjacoby.jobtrackerapi.dto.JobShortDTO;
import com.ismailjacoby.jobtrackerapi.entity.JobEntity;
import com.ismailjacoby.jobtrackerapi.entity.UserEntity;
import com.ismailjacoby.jobtrackerapi.enums.JobStatus;
import com.ismailjacoby.jobtrackerapi.exception.NotFoundException;
import com.ismailjacoby.jobtrackerapi.form.JobForm;
import com.ismailjacoby.jobtrackerapi.repository.JobRepository;
import com.ismailjacoby.jobtrackerapi.repository.UserRepository;
import com.ismailjacoby.jobtrackerapi.service.declaration.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobServiceImpl(JobRepository jobRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addJob(JobForm form, String username) {
        if(form == null) {
            throw new IllegalArgumentException("Form cannot be null");
        }

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

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
        job.setUser(user);
        jobRepository.save(job);
    }
    /*
    * TODO: Get One Job
    * Has to check for the logged in user
    * */
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
    public List<JobEntity> getAllJobs(String username) {
        return jobRepository.findByUserUsername(username);
    }

    @Override
    public void updateJob(Long id, JobForm form, String username) {
        if(form == null) {
            throw new IllegalArgumentException("Form cannot be null");
        }

        JobEntity job = jobRepository.findById(id)
                .filter(j -> j.getUser().getUsername().equals(username))
                .orElseThrow(()-> new NotFoundException("Job not found or unauthorized"));

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
    public void deleteJob(Long id, String username) {
        JobEntity job = jobRepository.findById(id)
                .filter(j -> j.getUser().getUsername().equals(username))
                .orElseThrow(()-> new NotFoundException("Job not found or unauthorized"));

        jobRepository.deleteById(id);
    }
}
