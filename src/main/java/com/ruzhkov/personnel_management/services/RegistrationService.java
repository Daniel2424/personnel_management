package com.ruzhkov.personnel_management.services;

import com.ruzhkov.personnel_management.entity.Person;
import com.ruzhkov.personnel_management.repositories.PeopleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person){
        String encoderPassword  =passwordEncoder.encode(person.getPassword());
        person.setPassword(encoderPassword);
        person.setRole("ROLE_USER");
        peopleRepository.save(person);
    }
}

