/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wuzzuf.jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

/**
 *
 * @author fady
 */
public final class WuzzufJobs implements DAOJobs {
    
    private static WuzzufJobs instance;
    private Dataset<Row> df;
    private final String PATH = "Wuzzuf_Jobs.csv";
    private final JavaRDD<Job> jobsRDD;
    private final SparkSession sparkSession;
    
    private WuzzufJobs() {
        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);
        sparkSession = SparkSession
                .builder()
                .appName("Wuzzuf Jobs")
                .master("local[4]")
                .getOrCreate ();
        final DataFrameReader dfFrameReader = sparkSession.read();
        dfFrameReader.option ("header", "true");
        df = dfFrameReader.csv(PATH);
        jobsRDD = df.toJavaRDD().map(Utilities::toJob).cache();
        clean();
        CreatMinYearsExp();
    }
    
    public static WuzzufJobs getInstance(){
       if(instance == null)
           instance = new WuzzufJobs();
       return instance;
    }

    @Override
    public void clean() {
        System.out.println ("Count : " + df.count());
        System.out.println("Remove Duplicates");
        df = df.dropDuplicates();
        System.out.println ("Count : " + df.count());
        System.out.println("Remove Nulls");
        df = df.na().drop();
        System.out.println ("Count : " + df.count());
    }

    @Override
    public List<Job> getJobs() {
        return jobsRDD.collect();
    }
    
    @Override
    public List<Job> getJobs(int n) {
        return jobsRDD.take(n);
    }

    @Override
    public List<List<Object>> summary() {
        df.printSchema();
        df.summary("count", "min", "max").show();
        List<Row> summary = df.summary("count", "min", "max").collectAsList();
        List<List<Object>> result = new ArrayList<>();
        for(Row row : summary) 
        {
            List<Object> lst = new ArrayList<>();
            for(int i = 0; i < row.size(); i++){
                lst.add(row.get(i));
            }
            result.add(lst);
        }
        return result;
    }
    
    @Override
    public void show(int n) {
        df.show(n);
    }
    
    private List<List<Object>> mapToObjectsList(List<Map.Entry> mylist) {
        List<List<Object>> result = new ArrayList<>();
        for(Map.Entry entry : mylist) 
        {
            List<Object> lst = new ArrayList<>();
            lst.add(entry.getKey());
            lst.add(entry.getValue());
            result.add(lst);
        }
        return result;
    }
    
    private List<List<Object>> helperMap(JavaRDD<String> input) {
        Map<String, Long> wordCounts = input.countByValue();
        List<Map.Entry> sorted = wordCounts.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());
        return mapToObjectsList(sorted);
}

    @Override
    public List<List<Object>> jobsPerCompany() {
        return helperMap(jobsRDD.map(j -> j.getCompany()));
    }

    @Override
    public List<List<Object>> mostJobTitles() {
        return helperMap(jobsRDD.map(j -> j.getTitle()));
    }

    @Override
    public List<List<Object>> mostPopularAreas() {
        return helperMap(jobsRDD.map(j -> j.getLocation()));
    }

    @Override
    public List<List<Object>> getSkillList() {
        JavaRDD<String> skills = jobsRDD.map(j -> j.getSkills());                                  
        JavaRDD<String> words = skills.
                flatMap (title -> Arrays.asList (title
                                                .toLowerCase ()
                                                .trim()
                                                .split ("\\s*\\p{Punct}\\s*"))
                                                .iterator())
                                                .filter(StringUtils::isNotBlank);
        List<Map.Entry> sorted = words.countByValue()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect (Collectors.toList());
        return mapToObjectsList(sorted);
        
    }
    
    @Override
    public void CreatMinYearsExp() {
        jobsRDD.foreach(j -> j.setMinYear(Utilities.minYear(j.getYearsExp())));
        df = sparkSession.createDataFrame(jobsRDD, Job.class);
    }

    @Override
    public long size() {
        return jobsRDD.count();
    }
    
    @Override
    public String[] columns() {
        return df.columns();
    }
    
}
