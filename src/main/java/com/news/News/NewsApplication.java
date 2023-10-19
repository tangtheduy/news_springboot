package com.news.News;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.news.News.Model.Admin;
import com.news.News.Repository.AdminRepository;

@SpringBootApplication
public class NewsApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(NewsApplication.class, args);
    }

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
//    	Admin temp = new Admin();
//		temp.setPassword(passwordEncoder.encode("admin"));
//		temp.setUsername("admin");
//		temp.setId(1);
//		adminRepository.save(temp);
//		System.out.print(temp);
    }
}