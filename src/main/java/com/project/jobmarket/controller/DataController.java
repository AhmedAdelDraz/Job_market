package com.project.jobmarket.controller;

import com.project.jobmarket.service.JobsTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 *
 * @author Draz
 */
@Controller
@RequestMapping(value = "/data")
public class DataController {

    @Autowired
    JobsTableService jobsRecords = new JobsTableService();

    @GetMapping("/jobs/table")
    String getAllJobs(Model model){
        model.addAttribute("jobs",jobsRecords.getJobsRecords());
        return "table";
    }
}
