package com.project.jobmarket.controller;

import com.project.jobmarket.service.AreaCountService;
import com.project.jobmarket.service.CompanyTitleCountService;
import com.project.jobmarket.service.SkillsCountService;
import com.project.jobmarket.service.TitleCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
/**
 *
 * @author Draz
 */
@Controller
@RequestMapping(value="/charts")
public class ChartController {

    @Autowired
    CompanyTitleCountService pieChartDataService = new CompanyTitleCountService();

    @Autowired
    TitleCountService titleBarChartDataService = new TitleCountService();

    @Autowired
    AreaCountService areaBarChartDataService = new AreaCountService();

    @Autowired
    SkillsCountService skillsBarChartDataService = new SkillsCountService();

    @GetMapping(value="/piechart")
    public String pieChart(Model model){
        model.addAttribute("chartData",pieChartDataService.jobsCompanyCount());
        return "piechart";
    }

    @GetMapping(value="/bar/areas")
    public String popularAreas(Model model){
        model.addAttribute("areaCount",areaBarChartDataService.areaCount());
        return "location";
    }

    @GetMapping(value="/bar/titles")
    public String popularTitles(Model model){
        model.addAttribute("titleCount",titleBarChartDataService.titleCount());
        return "title";
    }

    @GetMapping(value="/bar/skills")
    public String popularSkills(Model model){
        model.addAttribute("skillCount",skillsBarChartDataService.skillsCount());
        return "skill";
    }
}
