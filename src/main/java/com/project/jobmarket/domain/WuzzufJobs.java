/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.jobmarket.domain;

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
import org.springframework.stereotype.Component;

/**
 *
 * @author fady
 */
@Component
public class WuzzufJobs implements DAOJobs {
    
    private Dataset<Row> df;
    private final String PATH = "Wuzzuf_Jobs.csv";
    private final JavaRDD<Job> jobsRDD;
    private final SparkSession sparkSession;
    
    public WuzzufJobs() {
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
    public List<Job> getJobs(int n) {
        if (n == 0) return jobsRDD.collect();
        return jobsRDD.take(n);
    }

    @Override
    public List<List<Object>> summary() {
        return Arrays.asList(
                Arrays.asList("rows",df.count()),
                Arrays.asList("columns",this.columns().length),
                Arrays.asList("companies",this.jobsPerCompany((int)this.size()).stream().count()),
                Arrays.asList("titles",this.mostJobTitles((int)this.size()).stream().count()),
                Arrays.asList("locations",this.mostPopularAreas((int)this.size()).stream().count()),
                Arrays.asList("skills",this.getSkillList((int)this.size()).stream().count())
        );
    }
    
    @Override
    public void show(int n) {
        df.show(n);
    }
    
    private List<List<Object>> mapToObjectsList(List<Map.Entry> mylist, int n) {
        List<List<Object>> result = new ArrayList<>();
        for(Map.Entry entry : mylist) 
        {
            List<Object> lst = new ArrayList<>();
            lst.add(entry.getKey());
            lst.add(entry.getValue());
            result.add(lst);
        }
        if (n == 0) return result;
        return result.stream().limit(n).collect(Collectors.toList());
    }
    
    private List<List<Object>> helperMap(JavaRDD<String> input, int n) {
        Map<String, Long> wordCounts = input.countByValue();
        List<Map.Entry> sorted = wordCounts.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());
        return mapToObjectsList(sorted, n);
}

    @Override
    public List<List<Object>> jobsPerCompany(int n) {
        return helperMap(jobsRDD.map(j -> j.getCompany()), n);
    }

    @Override
    public List<List<Object>> mostJobTitles(int n) {
        return helperMap(jobsRDD.map(j -> j.getTitle()), n);
    }

    @Override
    public List<List<Object>> mostPopularAreas(int n) {
        return helperMap(jobsRDD.map(j -> j.getLocation()), n);
    }

    @Override
    public List<List<Object>> getSkillList(int n) {
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
        return mapToObjectsList(sorted, n);
        
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
    public String[] columns() { return df.columns(); }

}
