package com.ruzhkov.personnel_management.controllers;

import com.ruzhkov.personnel_management.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final PersonDetailsService personDetailsService;

    @Autowired
    public ProfileController(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }


    @GetMapping("/")
    public String showProfile( Model model){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        System.out.println(authentication.getName());
        System.out.println(userDetails.toString());
        System.out.println(personDetailsService.getPerson(authentication.getName()).getRole());
        model.addAttribute("infoAboutPerson", userDetails.toString());
        model.addAttribute("person", authentication.getName());
        model.addAttribute("role",personDetailsService.getPerson(authentication.getName()).getRole());




        return "/profile";
    }

}
