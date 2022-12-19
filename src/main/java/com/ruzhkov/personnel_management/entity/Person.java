package com.ruzhkov.personnel_management.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name="year_of_birth")
    private int yearOfBirth;
    @Email
    @Column(name="email")
    private String email;
    @NotEmpty(message = "Пароль некорректен")
    @Column(name="password")
    private String password;
    @Column(name = "role")
    private String role;


    @OneToMany(mappedBy = "person")
    private List<WorkMessage> messages;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
