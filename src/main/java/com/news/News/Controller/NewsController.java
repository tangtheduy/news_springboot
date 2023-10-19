package com.news.News.Controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.aspectj.weaver.patterns.IfPointcut.IfFalsePointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.news.News.Model.CategoryNews;
import com.news.News.Model.News;
import com.news.News.Repository.AdminRepository;
import com.news.News.Service.CategoryNewsService;
import com.news.News.Service.NewsService;

import net.coobird.thumbnailator.Thumbnails;

@Controller
public class NewsController {
	@Autowired
	NewsService newsService;
	@Autowired
	CategoryNewsService categoryNewsService;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ResourceLoader resourceLoader;

	
	// Controller cho trang web
	@GetMapping("/index")
	public String getAllNews(Model model) {
		model.addAttribute("category_list", categoryNewsService.getAllCategory());
		List<News> tempList = newsService.getAllNews();
		List<News> news_new = tempList.subList(0, 3);
		List<News> news = tempList.subList(3, tempList.size());
		model.addAttribute("news_new", news_new);
		model.addAttribute("news", news);
		return "news/index";
	}

	@GetMapping("/category={id}")
	public String getNewsbyId_category(Model model, @PathVariable("id") int id) {
		String name_category = categoryNewsService.findbyId(id).getName();
		model.addAttribute("category_list", categoryNewsService.getAllCategory());
		List<News> tempList = newsService.getNewsbyId_category(id);
		List<News> news_new = new ArrayList<News>();
		List<News> news = new ArrayList<News>();
		if(tempList.size()==0) {
			
		}
		else if(tempList.size()<3) {
			news_new = tempList;
		}
		else {
			news_new = tempList.subList(0, 3);
			news = tempList.subList(3, tempList.size());
		}
		model.addAttribute("name_category", name_category);
		model.addAttribute("news_new", news_new);
		model.addAttribute("news", news);
		return "news/news_category";
	}

	@GetMapping("/news={id}")
	public String getNewsbyId(Model model, @PathVariable("id") int id) {
		model.addAttribute("category_list", categoryNewsService.getAllCategory());
		List<CategoryNews> tempCategoryNews  = categoryNewsService.getAllCategory();
		News news = newsService.getNewsbyId(id);
		newsService.addView_count(news);
		model.addAttribute("news", news);
		return "news/news";
	}
	
	@GetMapping("/search")
    public String searchNews(@RequestParam("query") String query, Model model) {
		query = query.toLowerCase().trim();
        List<News> searchResults = newsService.searchTitle(query);
        model.addAttribute("query", query);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("category_list", categoryNewsService.getAllCategory());
        return "news/search_results"; // Tạo view hiển thị kết quả tìm kiếm
    }
	
	// Controller cho phần admin
	@GetMapping("/admin")
	public String adminPage() {
		return "admin/index";
	}
//	@GetMapping("/admin/listNews")
//	public String showListNews(Model model) {
//		List<News> tempList = newsService.getAllNews();
//		model.addAttribute("news", tempList);
//		model.addAttribute("category_list", categoryNewsService.getAllCategory());
//		return "admin/list_news";
//	}
	@GetMapping("/admin/listNews")
	public String showListNews(Model model, @RequestParam(value = "pageno", defaultValue = "1") Integer pageNo) {
		//List<News> tempList = newsService.getAllNews();
		Page<News> tempList = newsService.getAll(pageNo);
		int start_index = (pageNo-1)*10;
		model.addAttribute("start_index", start_index); // thêm index để làm STT của bảng
		model.addAttribute("totalPage", tempList.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("news", tempList);
		model.addAttribute("category_list", categoryNewsService.getAllCategory());
		return "admin/list_news";
	}
	@GetMapping("/admin/listNews/sortbyView")
	public String showListNews_sortbyView(Model model) {
		model.addAttribute("news", newsService.getAllNews_View());
		model.addAttribute("category_list", categoryNewsService.getAllCategory());
		return "admin/list_news";
	}
//	@GetMapping("/admin/category")
//	public String getNewsbyId_categoryAdmin(Model model, @RequestParam(value = "id", required = false) Integer id,@RequestParam(value = "pageno", defaultValue = "1") Integer pageNo) {
//		model.addAttribute("category_list", categoryNewsService.getAllCategory()); 
//		if(id == null ) {
//			List<News> news = newsService.getAllNews();
//			model.addAttribute("news", news);
//		}
//		else {
//			model.addAttribute("id_category", id);
//			List<News> news = newsService.getNewsbyId_category(id);
//			model.addAttribute("news", news);S
//		}
//		return "admin/list_news";
//	}
	@GetMapping("/admin/listCategory")
	public String showListCategory(Model model, @RequestParam(value = "pageno", defaultValue = "1") Integer pageNo) {
		//List<News> tempList = newsService.getAllNews();
		Page<CategoryNews> tempList = categoryNewsService.getAll(pageNo);
		int start_index = (pageNo-1)*10;
		model.addAttribute("start_index", start_index); // thêm index để làm STT của bảng
		model.addAttribute("totalPage", tempList.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("category_list", tempList);
		model.addAttribute("category_temp", new CategoryNews());
		return "admin/list_category";
	}
	@PostMapping("/add_category")
	public String addCategory(CategoryNews categoryNews,Model model,RedirectAttributes redirectAttributes,BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/listCategory";
		}
		CategoryNews temp = categoryNewsService.addCategory(categoryNews);
		if(temp != null) {
			redirectAttributes.addFlashAttribute("successMessage", "Bạn đã thêm danh mục thành công");
		}
		else {
			redirectAttributes.addFlashAttribute("failMessage", "Tên danh mục không được trùng nhau");
		}
		return "redirect:/admin/listCategory";
	}
	@GetMapping("/admin/category")
	public String getNewsbyId_categoryAdmin(Model model, @RequestParam(value = "id", required = false) Integer id,@RequestParam(value = "pageno", defaultValue = "1") Integer pageNo) {
		model.addAttribute("category_list", categoryNewsService.getAllCategory()); 
		if(id == null ) {
			Page<News> news = newsService.getAll(pageNo);
			model.addAttribute("news", news);
			model.addAttribute("totalPage", news.getTotalPages());
			model.addAttribute("currentPage", pageNo);
		}
		else {
			model.addAttribute("id_category", id);
			Page<News> news = newsService.getAll_byCategory(pageNo, id);
			model.addAttribute("news", news);
			model.addAttribute("totalPage", news.getTotalPages());
			model.addAttribute("currentPage", pageNo);
		}
		int start_index = (pageNo-1)*10;
		model.addAttribute("start_index", start_index); // thêm index để làm STT của bảng
		return "admin/list_news";
	}
	// Xóa bài viết
	@PostMapping("admin/delete/{id}")
	public String deleteNews(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
		newsService.deleteNews(id);

		redirectAttributes.addFlashAttribute("successMessage", "Bạn đã xóa thành công");
		return "redirect:/admin/listNews";
	}
	// Thêm bài viết
	@GetMapping("/admin/addNews")
	public String showAddForm(Model model) {
		model.addAttribute("news", new News());
		model.addAttribute("category_list", categoryNewsService.getAllCategory());
		return "admin/add_news";
	}
	
	@PostMapping("/add")
	public String addNews(News news, BindingResult result, Model model, RedirectAttributes redirectAttributes,
			@RequestParam("imageFile") MultipartFile file) {
		if (result.hasErrors()) {
			model.addAttribute("category_list", categoryNewsService.getAllCategory());
			return "admin/add_news";
		}
		if (!file.isEmpty()) {
			try {
				 String fileName = file.getOriginalFilename();

		            // Xác định đường dẫn tuyệt đối đến thư mục lưu trữ hình ảnh trên đĩa cứng
		            String uploadDirectory = "D:/workspace/News/src/main/resources/static/img_news";

		            // Lưu hình ảnh vào thư mục trên đĩa cứng
		            Path targetPath = Paths.get(uploadDirectory, fileName);
		            Thumbnails.of(file.getInputStream())
		            .size(992, 595)
		            .toFile(targetPath.toFile());
		            //Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

		            // Lấy đường dẫn của ảnh
		            String imagePath = "/img_news/" + fileName;
		            news.setImage(imagePath);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		newsService.addNews(news);
		redirectAttributes.addFlashAttribute("successMessage", "Bạn đã thêm bài thành công");
		return "redirect:/admin/listNews";
	}
	
	// Sửa bài viết
	@GetMapping("/admin/editNews={id}")
	public String showEditForm(Model model, @PathVariable("id") int id) {
		model.addAttribute("news", newsService.getNewsbyId(id));
		model.addAttribute("category_list", categoryNewsService.getAllCategory());
		return "admin/edit_news";
	}

	@PostMapping("/edit")
	public String editNews(News news, BindingResult result, Model model, RedirectAttributes redirectAttributes,@RequestParam("imageFile") MultipartFile file) {
		if (result.hasErrors()) {
			model.addAttribute("category_list", categoryNewsService.getAllCategory());
			return "admin/edit_news";
		}
		if (!file.isEmpty()) {
			try {
				 String fileName = file.getOriginalFilename();

		            // Xác định đường dẫn tuyệt đối đến thư mục lưu trữ hình ảnh trên đĩa cứng
		            String uploadDirectory = "D:/workspace/News/src/main/resources/static/img_news";

		            // Lưu hình ảnh vào thư mục trên đĩa cứng
		            Path targetPath = Paths.get(uploadDirectory, fileName);
		            Thumbnails.of(file.getInputStream())
		            .size(992, 595)
		            .toFile(targetPath.toFile());
		            //Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

		            // Lấy đường dẫn của ảnh mới
		            String imagePath = "/img_news/" + fileName;
		            
		            //Xóa ảnh cũ
		            Path deletePath = Paths.get(uploadDirectory, news.getImage());
		            Files.deleteIfExists(deletePath);
		            
		            //Update path mới vào news
		            news.setImage(imagePath);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		newsService.updateNews(news);
		redirectAttributes.addFlashAttribute("successMessage", "Bạn đã sửa bài thành công");
		return "redirect:/admin/listNews";
	}
	//Search bài viết
	@GetMapping("/admin/search")
    public String searchNews_Admin(@RequestParam("query") String query, Model model) {
		query = query.toLowerCase().trim();
        List<News> searchResults = newsService.searchTitle(query);
        model.addAttribute("query", query);
        model.addAttribute("news", searchResults);
        model.addAttribute("category_list", categoryNewsService.getAllCategory());
        return "admin/list_news"; // Tạo view hiển thị kết quả tìm kiếm
    }
	// Xóa danh mục
		@PostMapping("admin/delete_category/{id}")
		public String deleteCategory(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
			categoryNewsService.deleteCategory(id);
			redirectAttributes.addFlashAttribute("successMessage", "Bạn đã xóa thành công");
			return "redirect:/admin/listCategory";
		}
	
}
