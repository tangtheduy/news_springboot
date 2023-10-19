package com.news.News.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.news.News.Model.Admin;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
	@Query(value = "SELECT * FROM DUY_ADMIN  WHERE username = ?",nativeQuery = true)
    public Admin findByUsername(String username);
}
