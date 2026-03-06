package com.erp.authService.repo;


import com.erp.authService.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, String id);

    Optional<Group> findByName(String name);

    List<Group> findByAppModules_Id(String appModuleId);
}
