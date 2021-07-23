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
public class AreaCountService {
    @Autowired
    WuzzufJobs object;
    public List<List<Object>> areaCount() { return object.mostPopularAreas(10);}
}
