package com.ruzhkov.personnel_management.repositories;

import com.ruzhkov.personnel_management.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
}
