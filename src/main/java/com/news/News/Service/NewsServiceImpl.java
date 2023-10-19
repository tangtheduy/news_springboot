package com.news.News.Service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.news.News.Model.News;
import com.news.News.Repository.NewsRepository;

@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	NewsRepository newsRepository;

	@Override
	public List<News> getAllNews() {
		// TODO Auto-generated method stub
		return newsRepository.findAllCustom_Time();
	}
	
	@Override
	public List<News> getAllNews_View() {
		// TODO Auto-generated method stub
		return newsRepository.findAllCustom_View();
	}

	@Override
	public List<News> getNewsbyId_category(int id_category) {
		// TODO Auto-generated method stub
		List<News> news = newsRepository.getNewsbyId_category(id_category);
		return news;
	}

	@Override
	public News getNewsbyId(int id) {
		// TODO Auto-generated method stub
		News tempNews = newsRepository.findById(id).get();
		return tempNews;
	}

	@Override
	public void addNews(News news) {
		// TODO Auto-generated method stub
		Date currentDate = new Date();
		news.setTime_created(currentDate);
		news.setTime_modify(currentDate);
		newsRepository.save(news);
	}

	@Override
	public void updateNews(News news) {
		// TODO Auto-generated method stub
		Date currentDate = new Date();
		news.setTime_modify(currentDate);
		newsRepository.save(news);
	}

	@Override
	public void deleteNews(int id) {
		// TODO Auto-generated method stub
		News news = getNewsbyId(id);
		if (news.getImage() != null) {
			try {
				String uploadDirectory = "D:/workspace/News/src/main/resources/static";
				// Xóa ảnh cũ
				Path deletePath = Paths.get(uploadDirectory, news.getImage());
				Files.deleteIfExists(deletePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		newsRepository.deleteById(id);
	}

	@Override
	public void deleteNews(News news) {
		// TODO Auto-generated method stub
		if (news.getImage() != null) {
			try {
				String uploadDirectory = "D:/workspace/News/src/main/resources/static";
				// Xóa ảnh cũ
				Path deletePath = Paths.get(uploadDirectory, news.getImage());
				Files.deleteIfExists(deletePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		newsRepository.delete(news);;
	}

	@Override
	public void addView_count(News news) {
		// TODO Auto-generated method stub
		news.addView(); // ADD 1 to viewcount
		newsRepository.saveAndFlush(news);
	}

	@Override
	public List<News> searchTitle(String query) {
		// TODO Auto-generated method stub
		return newsRepository.searchByTitleLike(query);
	}
	//test phân trang
	@Override
	public Page<News> getAll(Integer pageNo) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo-1, 10);
		return newsRepository.findAllCustom_Time_Page(pageable);
	}

	@Override
	public Page<News> getAll_byCategory(Integer pageNo, Integer id) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo-1, 10);
		return newsRepository.getNewsbyId_category_Page(id,pageable);
	}
	
	
	
}
