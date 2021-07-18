package com.project.jobmarket.controller;

import com.project.jobmarket.service.AllJobsService;
import com.project.jobmarket.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/data")
public class DataController {
    @Autowired
    AllJobsService alljobs = new AllJobsService();

    @Autowired
    SummaryService dataSummary = new SummaryService();

    @GetMapping("/jobs/table")
    String getAllJobs(Model model){
        //model.addAttribute();
        return "jobs";
    }

    @GetMapping("/data/summary")
    String getSummaries(Model model){
        //model.addAttribute();
        return "summary";
    }

}
