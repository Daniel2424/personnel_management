package com.ruzhkov.personnel_management.repositories;

import com.ruzhkov.personnel_management.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String name);

    List<Person> findAllByRole(String role);
}
