package com.project.jobmarket.service;

import com.project.jobmarket.domain.WuzzufJobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 *
 * @author Draz
 */
@Service
public class TitleCountService {
    @Autowired
    WuzzufJobs object;
    public List<List<Object>> titleCount() { return object.mostJobTitles(10); }
}
