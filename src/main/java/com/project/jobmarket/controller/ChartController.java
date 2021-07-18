package com.project.jobmarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/charts")
public class ChartController {

    @GetMapping(value="/charts/piechart")
    public String pieChart(Model model){
        //model.addAttribute();
        return "piechart";
    }

    @GetMapping(value="/charts/bar/areas")
    public String popularAreas(Model model){
        //model.addAttribute();
        return "barchart";
    }

    @GetMapping(value="/charts/bar/titles")
    public String popularTitles(Model model){
        //model.addAttribute();
        return "barchart";
    }
}
