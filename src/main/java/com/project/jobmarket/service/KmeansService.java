package com.project.jobmarket.service;

import com.project.jobmarket.domain.WuzzufJobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KmeansService {
    @Autowired
    WuzzufJobs object;

    public List<Integer> getCluster(int clsuterNumber){
        object.KMeans(clsuterNumber);
        return object.getPrediction();
    }
}
