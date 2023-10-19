package com.news.News.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "DUY_NEWS")
public class News {

	@Id
	@SequenceGenerator(name = "seqnews", sequenceName = "DUY_NEWS_SEQ", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqnews")
	private int id;
	
	//@NotBlank(message = "Tiêu đề không được để trống")
	//@Size(max = 200, message = "{Độ dài không quá 200}")
	@Column(name ="TITLE")
	
	private String title;
	//@NotBlank(message = "Tiêu đề ngắn không được để trống")
	//@Size(max = 200, message = "{Độ dài không quá 200}")
	@Column(name ="TITLE_SHORT")
	private String title_short;
	
	//@NotBlank(message = "Nội dung không được để trống")
	//@Column(name ="CONTENT")
	private String content;
	
	//@NotBlank(message = "Mô tả không được để trống")
	//@Size(max = 600, message = "{Độ dài không quá 600}")
	@Column(name ="DESCRIPTION")
	private String description;
	
	@Column(name ="IMAGE")
	private String image;
	
	@Column(name ="ID_CATEGORY")
	private int id_category;
	
	@Column(name ="TIME_CREATED")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date time_created;
	
	@Column(name ="TIME_MODIFY")
	private Date time_modify;
	
	@Column(name ="COUNT_VIEW")
	private int view;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category", referencedColumnName = "id",insertable = false, updatable = false)
    private CategoryNews category;
	public News() {
		
	}
	
	

	public News(String title, String title_short, String content, String description, String image, int id_category,
			Date time_created, Date time_modify, int view, CategoryNews category) {
		super();
		this.title = title;
		this.title_short = title_short;
		this.content = content;
		this.description = description;
		this.image = image;
		this.id_category = id_category;
		this.time_created = time_created;
		this.time_modify = time_modify;
		this.view = view;
		this.category = category;
	}



	public News(int id, String title, String title_short, String content, String description, String image,
			int id_category, Date time_created, Date time_modify, int view, CategoryNews category) {
		super();
		this.id = id;
		this.title = title;
		this.title_short = title_short;
		this.content = content;
		this.description = description;
		this.image = image;
		this.id_category = id_category;
		this.time_created = time_created;
		this.time_modify = time_modify;
		this.view = view;
		this.category = category;
	}



	public String getTitle_short() {
		return title_short;
	}



	public void setTitle_short(String title_short) {
		this.title_short = title_short;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getId_category() {
		return id_category;
	}
	public void setId_category(int id_category) {
		this.id_category = id_category;
	}
	public Date getTime_created() {
		return time_created;
	}
	public void setTime_created(Date time_created) {
		this.time_created = time_created;
	}


	public int getView() {
		return view;
	}


	public void setView(int view) {
		this.view = view;
	}


	public CategoryNews getCategory() {
		return category;
	}


	public void setCategory(CategoryNews category) {
		this.category = category;
	}


	public Date getTime_modify() {
		return time_modify;
	}


	public void setTime_modify(Date time_modify) {
		this.time_modify = time_modify;
	}
	
	public void addView() {
		this.view++;
	}
}
