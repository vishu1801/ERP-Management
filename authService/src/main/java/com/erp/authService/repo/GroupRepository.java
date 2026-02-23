package com.erp.authService.repo;

import com.erp.authService.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group,String> {

    boolean existsByName(String name);

    Optional<Group> findByName(String name);

//    @Query("SELECT g FROM Group g LEFT JOIN FETCH g.app_modules WHERE g.id = :id")
//    Optional<Group> findByIdWithModules(Long id);
}