package com.news.News.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.news.News.Model.News;




@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
	@Query(value="SELECT * FROM DUY_NEWS where id_category = ? ORDER BY TIME_MODIFY DESC", nativeQuery = true)	
	List<News> getNewsbyId_category(int id );
	
	@Query(value="SELECT * FROM DUY_NEWS ORDER BY TIME_MODIFY DESC", nativeQuery = true)	
	List<News> findAllCustom_Time();
	
	@Query(value="SELECT * FROM DUY_NEWS ORDER BY COUNT_VIEW DESC", nativeQuery = true)	
	List<News> findAllCustom_View();
	
	@Query(value ="SELECT * FROM DUY_NEWS WHERE LOWER(TITLE) LIKE %?% " , nativeQuery = true)
	List<News> searchByTitleLike(String title);
	
	@Query(value="SELECT * FROM DUY_NEWS ORDER BY TIME_MODIFY DESC", nativeQuery = true)	
	Page<News> findAllCustom_Time_Page(Pageable pageable);
	
	@Query(value="SELECT * FROM DUY_NEWS where id_category = ? ORDER BY TIME_MODIFY DESC", nativeQuery = true)	
	Page<News> getNewsbyId_category_Page(int id ,Pageable pageable);
}
