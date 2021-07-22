package com.project.jobmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class ApplicationLauncher{
    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);
        SpringApplication.run(ApplicationLauncher.class, args);
    }

}
