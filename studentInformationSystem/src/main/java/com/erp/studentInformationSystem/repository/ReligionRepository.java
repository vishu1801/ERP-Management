package com.erp.studentInformationSystem.repository;

import com.erp.studentInformationSystem.model.AcademicYear;
import com.erp.studentInformationSystem.model.Religion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReligionRepository extends JpaRepository<Religion, String> {
}
