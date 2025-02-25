import { JobStatus } from './job-status';

export interface Job {
  id: number;
  company: string;
  title: string;
  location: string;
  status: JobStatus;
  dateApplied: Date;
  link: string;
  description: string;
  salary: number;
  notes: string;
}
