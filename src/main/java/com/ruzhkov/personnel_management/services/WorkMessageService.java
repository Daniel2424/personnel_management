package com.ruzhkov.personnel_management.services;

import com.ruzhkov.personnel_management.entity.Person;
import com.ruzhkov.personnel_management.entity.WorkMessage;
import com.ruzhkov.personnel_management.repositories.PeopleRepository;
import com.ruzhkov.personnel_management.repositories.WorkMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class WorkMessageService {
    private final WorkMessageRepository workMessageRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public WorkMessageService(WorkMessageRepository workMessageRepository, PeopleRepository peopleRepository) {
        this.workMessageRepository = workMessageRepository;
        this.peopleRepository = peopleRepository;
    }
    @Transactional
    public void save(WorkMessage workMessage){
        workMessageRepository.save(workMessage);
    }

    public List<WorkMessage> findAll(){
        return workMessageRepository.findAll();
    }
    public List<WorkMessage> getListUser(String authentication, List<WorkMessage> list){
        //for User
        List<WorkMessage> listUser = new ArrayList<>(list);
        List<WorkMessage> listMessages= peopleRepository.findByName("admin")
                .get()
                .getMessages()
                .stream()
                .filter(x -> x.getMessage_sender().equals(authentication)).toList();

        listUser.addAll(listMessages);
        listUser.sort(Comparator.comparing(WorkMessage::getDate));
        Collections.reverse(listUser);
        return listUser;
        //
    }
    public List<WorkMessage> getListAdmin( List<WorkMessage> list){
        //for Admin
        List<WorkMessage> listAdmin = new ArrayList<>(list);
        List<WorkMessage> listMessagesOfAdmin = workMessageRepository
                .findAll()
                .stream()
                .filter(x -> x.getMessage_sender().equals("admin")).toList();
        listAdmin.addAll(listMessagesOfAdmin);
        listAdmin.sort(Comparator.comparing(WorkMessage::getDate));
        Collections.reverse(listAdmin);
        return listAdmin;
        //
    }
    @Transactional
    public void saveMessagesFromAdmin(int idPerson, String workMessage){
        Person person1 = peopleRepository.findById(idPerson).get();
        WorkMessage workMessage1 = new WorkMessage();

        Timestamp date = new Timestamp(System.currentTimeMillis());
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        workMessage1.setMessage_sender(authentication.getName());
        workMessage1.setPerson(person1);
        workMessage1.setDate(date);
        workMessage1.setWork_message(workMessage);
        workMessage1.setMessage_recipient(person1.getName());

        person1.getMessages().add(workMessage1);

        workMessageRepository.save(workMessage1);
    }
    @Transactional
    public void saveMessagesFromUsers( String workMessage){
        Person person1 = peopleRepository.findById(17).get();
        WorkMessage workMessage1 = new WorkMessage();

        Timestamp date = new Timestamp(System.currentTimeMillis());
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        workMessage1.setMessage_sender(authentication.getName());
        workMessage1.setPerson(person1);
        workMessage1.setDate(date);
        workMessage1.setWork_message(workMessage);
        workMessage1.setMessage_recipient("admin");


        person1.getMessages().add(workMessage1);

        workMessageRepository.save(workMessage1);
    }
    public List<WorkMessage> getListWith(int idPerson) {
        List<WorkMessage> list = peopleRepository
                .findById(idPerson)
                .get()
                .getMessages();
        List<WorkMessage> list2 = peopleRepository
                .findByName("admin")
                .get()
                .getMessages()
                .stream()
                .filter(x->x.getMessage_sender().equals(peopleRepository.findById(idPerson).get().getName()))
                .collect(Collectors.toList());
        list.addAll(list2);
        list.sort(Comparator.comparing(WorkMessage::getDate));
        Collections.reverse(list);
        return list;
    }
}
