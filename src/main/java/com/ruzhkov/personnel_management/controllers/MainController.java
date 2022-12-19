package com.ruzhkov.personnel_management.controllers;

import com.ruzhkov.personnel_management.entity.News;
import com.ruzhkov.personnel_management.repositories.NewsRepository;
import com.ruzhkov.personnel_management.services.NewsService;
import com.ruzhkov.personnel_management.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;


@Controller
public class MainController {
    private final PersonDetailsService personDetailsService;

    private final NewsService newsService;

    @Autowired
    public MainController(PersonDetailsService personDetailsService, NewsService newsService) {
        this.personDetailsService = personDetailsService;
        this.newsService = newsService;
    }

    @GetMapping("/")
    public String showFirstPage(Model model) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if(!authentication.getName().equals("anonymousUser")){
            model.addAttribute("role",personDetailsService.getPerson(authentication.getName()).getRole());

        }else{
            model.addAttribute("role", "ROLE_ANON");
        }
        model.addAttribute("all_news", newsService.getSomeNews());
        model.addAttribute("person", authentication.getName());
        return "/index";
    }


}
