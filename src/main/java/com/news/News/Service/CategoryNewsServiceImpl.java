package com.news.News.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.news.News.Model.CategoryNews;
import com.news.News.Model.News;
import com.news.News.Repository.CategoryNewsRepository;

@Service
public class CategoryNewsServiceImpl implements CategoryNewsService {
	@Autowired
	CategoryNewsRepository categoryNewsRepository;
	@Autowired
	NewsService newsService;
	@Override
	public CategoryNews addCategory(CategoryNews categoryNews) {
		// TODO Auto-generated method stub
		if(categoryNewsRepository.findByName(categoryNews.getName()) == null){
			return categoryNewsRepository.save(categoryNews);
		}
		return null;
	}

	@Override
	public void updateCategory(CategoryNews categoryNews) {
		// TODO Auto-generated method stub
		categoryNewsRepository.save(categoryNews);
	}

	@Override
	public void deleteCategory(int id) {
		// TODO Auto-generated method stub
		List<News> tempList =  newsService.getNewsbyId_category(id);
		for(News news : tempList) {
			newsService.deleteNews(news);
		}
		categoryNewsRepository.deleteById(id);
	}

	@Override
	public List<CategoryNews> getAllCategory() {
		// TODO Auto-generated method stub
		return categoryNewsRepository.findAll();
	}

	@Override
	public Page<CategoryNews> getAll(Integer pageNo) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo-1, 10);
		return categoryNewsRepository.findAll(pageable);
	}

	@Override
	public CategoryNews findbyId(Integer id) {
		// TODO Auto-generated method stub
		return categoryNewsRepository.findById(id).get();
	}
	
	
}
