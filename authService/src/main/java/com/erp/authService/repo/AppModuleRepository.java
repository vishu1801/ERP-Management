package com.erp.authService.repo;

import com.erp.authService.entity.AppModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppModuleRepository extends JpaRepository<AppModule, String> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, String id);

    Optional<AppModule> findByName(String name);
}
