package com.project.jobmarket.service;

import com.project.jobmarket.domain.Job;
import com.project.jobmarket.domain.WuzzufJobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 *
 * @author Draz
 */
@Service
public class JobsTableService {
    @Autowired
    WuzzufJobs object;
    public List<Job> getJobsRecords(){ return object.getJobs(10);}
}
