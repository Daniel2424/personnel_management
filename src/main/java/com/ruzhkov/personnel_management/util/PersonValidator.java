package com.ruzhkov.personnel_management.util;

import com.ruzhkov.personnel_management.entity.Person;
import com.ruzhkov.personnel_management.services.PersonDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class PersonValidator implements Validator {
    private final PersonDetailsService personDetailsService;

    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person)o;

        try {
            personDetailsService.loadUserByUsername(person.getName());
        }catch (UsernameNotFoundException ignored){
            return;
        }

        errors.rejectValue("name","","Человек с таким именем пользователя уже существует");
    }
}
