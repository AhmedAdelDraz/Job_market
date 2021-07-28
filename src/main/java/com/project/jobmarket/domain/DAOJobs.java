/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.jobmarket.domain;

import java.util.List;

/**
 *
 * @author fady
 */
public interface DAOJobs {
    public void clean();
    public List<Job> getJobs(int n);
    public List<List<Object>> summary();
    public void show(int n);
    public List<List<Object>> jobsPerCompany(int n);
    public List<List<Object>> mostJobTitles(int n);
    public List<List<Object>> mostPopularAreas(int n);
    public List<List<Object>> getSkillList(int n);
    public void CreatMinYearsExp();
    public long size();
    public String[] columns();
    public void KMeans(int numClusters);
    public Integer getPrediction(int n);
}
