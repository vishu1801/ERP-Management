package com.erp.studentInformationSystem.repository;

import com.erp.studentInformationSystem.model.Caste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasteRepository extends JpaRepository<Caste, String> {
}
