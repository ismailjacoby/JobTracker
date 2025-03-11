package com.ismailjacoby.jobtrackerapi.service.implementation;

import com.ismailjacoby.jobtrackerapi.dto.JobDTO;
import com.ismailjacoby.jobtrackerapi.dto.JobShortDTO;
import com.ismailjacoby.jobtrackerapi.entity.JobEntity;
import com.ismailjacoby.jobtrackerapi.entity.UserEntity;
import com.ismailjacoby.jobtrackerapi.enums.JobStatus;
import com.ismailjacoby.jobtrackerapi.exception.NotFoundException;
import com.ismailjacoby.jobtrackerapi.exception.UnauthorizedException;
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

    /**
     * Adds a new job application for the authenticated user.
     *
     * @param form The job application details.
     * @param username The username of the authenticated user.
     * @throws NotFoundException if the user is not found.
     * @throws IllegalArgumentException if the form is null.
     */
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


    /**
     * Retrieves a job by its ID, ensuring the user is authorized.
     *
     * @param id The job ID.
     * @param username The username of the authenticated user.
     * @return The job entity.
     * @throws NotFoundException if the job does not exist.
     * @throws UnauthorizedException if the user does not own the job.
     */
    @Override
    public JobEntity getJobById(Long id, String username) {
        return jobRepository.findById(id)
                .map(job->{
                    if(!job.getUser().getUsername().equals(username)) {
                        throw new UnauthorizedException("You are not authorized to access this job.");
                    }
                    return job;
                })
                .orElseThrow(()-> new NotFoundException("Job not found."));
    }

    /**
     * Retrieves all job applications associated with the authenticated user.
     *
     * @param username The username of the authenticated user.
     * @return A list of job entities.
     */
    @Override
    public List<JobEntity> getAllJobs(String username) {
        return jobRepository.findByUserUsername(username);
    }

    /**
     * Updates an existing job application, ensuring the user is authorized.
     *
     * @param id The job ID.
     * @param form The updated job details.
     * @param username The username of the authenticated user.
     * @throws NotFoundException if the job does not exist or the user is not authorized.
     * @throws IllegalArgumentException if the form is null.
     */
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

    /**
     * Deletes a job application, ensuring the user is authorized.
     *
     * @param id The job ID.
     * @param username The username of the authenticated user.
     * @throws NotFoundException if the job does not exist or the user is not authorized.
     */
    @Override
    public void deleteJob(Long id, String username) {
        JobEntity job = jobRepository.findById(id)
                .filter(j -> j.getUser().getUsername().equals(username))
                .orElseThrow(()-> new NotFoundException("Job not found or unauthorized"));

        jobRepository.deleteById(id);
    }
}
