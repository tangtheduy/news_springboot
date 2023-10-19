package com.news.News.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.news.News.Model.CategoryNews;

public interface CategoryNewsService {
	public List<CategoryNews> getAllCategory();
	public CategoryNews addCategory(CategoryNews categoryNews);
	public void updateCategory(CategoryNews categoryNews);
	public void deleteCategory(int id);
	public Page<CategoryNews> getAll(Integer pageNo);
	public CategoryNews findbyId(Integer id);
}
