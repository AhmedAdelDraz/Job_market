package com.project.jobmarket.service;

import com.project.jobmarket.domain.WuzzufJobs;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 *
 * @author Draz
 */
@Service
public class CompanyTitleCountService {
    WuzzufJobs object = WuzzufJobs.getInstance();
    public List<List<Object>> jobsCompanyCount(){ return object.jobsPerCompany(10);}
}
