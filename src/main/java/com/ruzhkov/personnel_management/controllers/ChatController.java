package com.ruzhkov.personnel_management.controllers;

import com.ruzhkov.personnel_management.entity.Person;
import com.ruzhkov.personnel_management.entity.WorkMessage;
import com.ruzhkov.personnel_management.models.ChatMessage;
import com.ruzhkov.personnel_management.services.PersonDetailsService;
import com.ruzhkov.personnel_management.services.WorkMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ChatController {
    private final WorkMessageService workMessageService;

    private final PersonDetailsService personDetailsService;

    @Autowired
    public ChatController(WorkMessageService workMessageService, PersonDetailsService personDetailsService) {
        this.workMessageService = workMessageService;
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("/workers/chat")
    public String chat(Model model){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        model.addAttribute("name", authentication.getName());
        return "/chat";
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload final ChatMessage chatMessage){
        return chatMessage;
    }

    @MessageMapping("/chat.newUser")
    @SendTo("/topic/public")
    public ChatMessage newUser(@Payload final ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @GetMapping("/messages")
    public String showMessages(Model model, @ModelAttribute("mes") WorkMessage workMessage, @ModelAttribute("person") Person person){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        List<WorkMessage> list = personDetailsService.getPerson(authentication.getName()).getMessages();
        List<WorkMessage> listUser = workMessageService.getListUser(authentication.getName(), list);
        List<WorkMessage> listAdmin = workMessageService.getListAdmin(list);
        List<String> listRole = List.of("ROLE_EMPLOYEE");

        model.addAttribute("mes_user", listUser);// <---------
        model.addAttribute("mes_admin", listAdmin);//<------
        model.addAttribute("allPerson", personDetailsService.getAllPersonWithRole("ROLE_EMPLOYEE"));
        model.addAttribute("role", personDetailsService.getPerson(authentication.getName()).getRole());

        return "messagesFrom";
    }

    @PostMapping("/messages/send")
    public String saveMessagesFromAdmin(Model model, @ModelAttribute("person") Person person, @ModelAttribute("mes")WorkMessage workMessage) {
        workMessageService.saveMessagesFromAdmin(person.getId(), workMessage.getWork_message());

        return "redirect:/messages";
    }
    @PostMapping("/messages/send-user")
    public String saveMessagesFromUsers(Model model, @ModelAttribute("person") Person person, @ModelAttribute("mes")WorkMessage workMessage) {
        workMessageService.saveMessagesFromUsers(workMessage.getWork_message());

        return "redirect:/messages";
    }
    @GetMapping("/messages/with")
    public String chatWith(@ModelAttribute("person") Person person, Model model, @ModelAttribute("mes")WorkMessage workMessage){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        List<WorkMessage> list = workMessageService.getListWith(person.getId());

        model.addAttribute("name",personDetailsService.loadUserById(person.getId()).getName());
        model.addAttribute("idPerson", person.getId());
        model.addAttribute("mes_admin", list);
        model.addAttribute("role", personDetailsService.getPerson(authentication.getName()).getRole());
        return "/messagesAdminWith";
    }
    @PostMapping("/messages/send/{id}")
    public String saveMessagesFromAdmin2( @ModelAttribute("mes")WorkMessage workMessage, @PathVariable("id") int id, @ModelAttribute("person")Person person) {
        workMessageService.saveMessagesFromAdmin(id, workMessage.getWork_message());
        person.setId(id);

        return "redirect:/messages";
    }
}
