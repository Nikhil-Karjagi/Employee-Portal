package com.example.employeeportal.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        // Data seeding disabled to prevent overriding UI changes and manual DB records
    }
}