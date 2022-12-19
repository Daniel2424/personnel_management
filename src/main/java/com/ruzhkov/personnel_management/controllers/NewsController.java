package com.ruzhkov.personnel_management.controllers;

import com.ruzhkov.personnel_management.entity.News;
import com.ruzhkov.personnel_management.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;

@Controller
public class NewsController {
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news/add")
    public String test(@ModelAttribute("news_obj") News news) {
        return "/addNews";
    }

    @PostMapping("/news/add")
    public String publish(@ModelAttribute("news_obj") News news){
        news.setNews_date(new Timestamp(System.currentTimeMillis()));
        newsService.save(news);

        return "redirect:/";
    }

    @GetMapping("/news")
    public String showNews(Model model){
        model.addAttribute("all_news", newsService.getAllNews());
        return "/allNews";
    }
    @GetMapping("/news/{id}")
    public String showNewsById(Model model, @PathVariable("id") int id){
        model.addAttribute("newsById", newsService.getNewsById(id));
        return "/newsById";
    }


}
