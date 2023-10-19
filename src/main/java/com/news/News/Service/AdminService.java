package com.news.News.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.news.News.Model.Admin;
import com.news.News.Repository.AdminRepository;

public class AdminService implements UserDetailsService  {

	@Autowired
    private AdminRepository adminRepository;
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("Admin not found");
        }
        return new AdminDetail(admin);
    }
}
