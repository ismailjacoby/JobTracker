package com.ismailjacoby.jobtrackerapi.enums;

public enum JobStatus {
    APPLIED,          // Application submitted
    INTERVIEW_SCHEDULED, // Interview is scheduled
    INTERVIEWED,      // Interview completed, waiting for a response
    OFFER_RECEIVED,   // Received a job offer
    ACCEPTED,         // Offer accepted
    REJECTED,         // Application or offer rejected
    WITHDRAWN,        // Candidate withdrew the application
    NO_RESPONSE       // No response from employer
}
