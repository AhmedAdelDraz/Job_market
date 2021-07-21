/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wuzzuf.jobs;

import java.util.List;
import java.util.Map;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

/**
 *
 * @author fady
 */
public interface DAOJobs {
    public void clean();
    public JavaRDD<Job> getJobs();
    public List<Job> getJobs(int n);
    public long size();
    public Dataset<Row> summary();
    public void show(int n);
    public List<List<Object>> jobsPerCompany();
    public List<List<Object>> mostJobTitles();
    public List<List<Object>> mostPopularAreas();
    public List<Map.Entry<String, Long>> getSkillList();
    public void CreatMinYearsExp();
}
