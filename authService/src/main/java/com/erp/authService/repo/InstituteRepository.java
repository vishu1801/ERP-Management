package com.erp.authService.repo;

import com.erp.authService.model.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteRepository extends JpaRepository<Institute,String> {
}
