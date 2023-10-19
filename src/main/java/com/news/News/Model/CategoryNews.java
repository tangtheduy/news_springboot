package com.news.News.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DUY_CATEGORY_NEWS")
public class CategoryNews {
	
	@Id
	@SequenceGenerator(name = "seqcategory", sequenceName = "DUY_CATEGORY_SEQ", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqcategory")
	private int id;
	
	@Column(name ="NAME")
	private String name;
	
	
	public CategoryNews() {
		super();
	}

	public CategoryNews(String name) {
		super();
		this.name = name;
	}

	public CategoryNews(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	 @Override
	    public String toString() {
	        return name;
	    }
}
