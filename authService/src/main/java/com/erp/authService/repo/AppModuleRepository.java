package com.erp.authService.repo;

import com.erp.authService.entity.AppModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AppModuleRepository extends JpaRepository<AppModule,String> {
    boolean existsByName(String name);

    Set<AppModule> findAllByIdIn(Set<Long> ids);
}
