package com.ruzhkov.personnel_management.controllers;

import com.ruzhkov.personnel_management.entity.News;
import com.ruzhkov.personnel_management.entity.Person;
import com.ruzhkov.personnel_management.services.NewsService;
import com.ruzhkov.personnel_management.services.PersonDetailsService;
import com.ruzhkov.personnel_management.services.WorkMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PersonDetailsService personDetailsService;

    private final WorkMessageService workMessageService;

    private final NewsService newsService;



    @Autowired
    public AdminController(PersonDetailsService personDetailsService, WorkMessageService workMessageService, NewsService newsService) {
        this.personDetailsService = personDetailsService;
        this.workMessageService = workMessageService;
        this.newsService = newsService;
    }


    @GetMapping()
    public String adminPage(Model model, @ModelAttribute("person")Person person) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        List<Person> allPerson = personDetailsService.getAllPerson();
        model.addAttribute("name", authentication.getName());
        model.addAttribute("allPerson", allPerson);
        model.addAttribute("listEmployee", personDetailsService.getAllPersonWithRole("ROLE_EMPLOYEE"));

        if(!authentication.getName().equals("anonymousUser")){
            model.addAttribute("role",personDetailsService.getPerson(authentication.getName()).getRole());

        }else{
            model.addAttribute("role", "ROLE_ANON");
        }
        return "/admin";
    }

    @PostMapping("/delete/")
    public String deleteUser(@ModelAttribute("person") Person person){
        personDetailsService.deleteUserById(person.getId());
        return "redirect:/admin";
    }

    @PostMapping("/employee/add")
    public String changeRoleOnEmployee(@ModelAttribute("person") Person person){
        System.out.println(person.getId());
        personDetailsService.changeRoleOnEmployee(person.getId());
        return "redirect:/admin";
    }






}
