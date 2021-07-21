/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wuzzuf.jobs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.spark.sql.Row;

/**
 *
 * @author fady
 */
public class Utilities{
    public static Job toJob(Row row) {
        return new Job(row.getString(0), row.getString(1), row.getString(2), row.getString(3),
                row.getString(4), row.getString(5), row.getString(6), row.getString(7));
    }
    
    public static String minYear(String s) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(s);
        if (m.find()) {
            return m.group(0);
        }
        return "0";
    }
}
