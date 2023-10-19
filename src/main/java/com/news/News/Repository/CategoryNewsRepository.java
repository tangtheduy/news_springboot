package com.news.News.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.news.News.Model.CategoryNews;

@Repository
public interface CategoryNewsRepository extends JpaRepository<CategoryNews,Integer> {
	CategoryNews findByName(String name);

}
