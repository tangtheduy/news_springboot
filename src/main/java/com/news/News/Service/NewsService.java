package com.news.News.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.news.News.Model.News;

public interface NewsService {
	public List<News> getAllNews();
	public List<News> getNewsbyId_category(int id_category);
	public News getNewsbyId(int id);
	public void addNews(News news);
	public void updateNews(News news);
	public void deleteNews(int id);
	public void deleteNews(News news);
	public void addView_count(News news);
	public List<News> searchTitle(String query);
	public List<News> getAllNews_View();
	
	
	//test phân trang
	public Page<News> getAll(Integer pageNo);
	public Page<News> getAll_byCategory(Integer pageNo,Integer id);
}
