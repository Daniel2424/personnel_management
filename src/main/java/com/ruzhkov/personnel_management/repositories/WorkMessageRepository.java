package com.ruzhkov.personnel_management.repositories;

import com.ruzhkov.personnel_management.entity.WorkMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkMessageRepository extends JpaRepository<WorkMessage, Integer> {
}
