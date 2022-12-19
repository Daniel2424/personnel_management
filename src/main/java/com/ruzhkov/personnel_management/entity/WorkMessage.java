package com.ruzhkov.personnel_management.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "workmessage")
@NoArgsConstructor
public class WorkMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String work_message;

    @Column(name = "message_date")
    private Timestamp date;

    private String message_sender;

    private String message_recipient;


    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;
}
