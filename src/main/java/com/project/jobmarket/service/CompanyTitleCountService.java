package com.project.jobmarket.service;

import com.project.jobmarket.domain.WuzzufJobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
/**
 *
 * @author Draz
 */
@Service
public class CompanyTitleCountService {
    @Autowired
    WuzzufJobs object;
    public List<List<Object>> jobsCompanyCount(){
        List<List<Object>> total = object.jobsPerCompany(0);
        List<List<Object>> top = object.jobsPerCompany(10);
        List<List<Object>> rest = total.subList(10,total.size());
        top.add(Arrays.asList("other",rest.stream().count()));
        return top;}
}
