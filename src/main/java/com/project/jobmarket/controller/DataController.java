package com.project.jobmarket.controller;

//import com.project.jobmarket.service.JobsTableService;
//import com.project.jobmarket.service.KmeansService;
//import com.project.jobmarket.service.SummaryService;
import com.project.jobmarket.service.JobsTableService;
import com.project.jobmarket.service.KmeansService;
import com.project.jobmarket.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Draz
 */
@Controller
@RequestMapping(value = "/data")
public class DataController {
    @Autowired
    JobsTableService jobsRecords = new JobsTableService();

    @Autowired
    SummaryService recordSummary = new SummaryService();

    @Autowired
    KmeansService jobsClusters = new KmeansService();

    @GetMapping("/jobs/table")
    String getAllJobs(Model model){
        model.addAttribute("jobs",jobsRecords.getJobsRecords());
        return "table";
    }

    @GetMapping("/jobs/summary")
    String getSummary(Model model){
        model.addAttribute("summary",recordSummary.getSummary());
        return "summary";
    }

    @GetMapping("/jobs/clusters/{clusterNumber}")
    String getCluster(@PathVariable("clusterNumber") int clusterNumber,Model model){
        model.addAttribute("clusters",jobsClusters.getCluster(clusterNumber));
        model.addAttribute("jobs",jobsRecords.getJobsRecords());
        return "kmean";
    }
}
