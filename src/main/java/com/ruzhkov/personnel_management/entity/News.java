package com.ruzhkov.personnel_management.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "news")
@Data
@NoArgsConstructor
public class News {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "news")
    private String news;

    @Column(name = "news_date")
    private Timestamp news_date;

    private String topic;

    private String presented;


}
