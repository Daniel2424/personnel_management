package com.ruzhkov.personnel_management.services;

import com.ruzhkov.personnel_management.entity.Person;
import com.ruzhkov.personnel_management.repositories.PeopleRepository;
import com.ruzhkov.personnel_management.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PersonDetailsService( PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByName(s);
        if(person.isEmpty()){
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return new PersonDetails(person.get());
    }
    public Person loadUserById(int id){
        return peopleRepository.getById(id);
    }
    public Person getPerson(String username) {
        return peopleRepository.findByName(username).get();
    }


    public void deleteUserById(int id){
        peopleRepository.deleteById(id);
    }



    public List<Person> getAllPerson(){
        List<Person> listPerson = peopleRepository.findAll();
        return listPerson;
    }


    public void changeRoleOnEmployee(int id) {
        Person person = peopleRepository.findById(id).get();
        person.setRole("ROLE_EMPLOYEE");
        peopleRepository.save(person);
    }
    public List<Person> getAllPersonWithRole(String role){
        return peopleRepository.findAllByRole(role);
    }
}
