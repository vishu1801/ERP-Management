package com.erp.authService.repo;

import com.erp.authService.entity.GroupModulePageAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupModulePageAccessRepository extends JpaRepository<GroupModulePageAccess, String> {

    List<GroupModulePageAccess> findByGroup_Id(String groupId);

    List<GroupModulePageAccess> findByGroup_IdAndAppModule_Id(String groupId, String appModuleId);

    List<GroupModulePageAccess> findByGroup_IdAndIsAccessibleTrue(String groupId);

    List<GroupModulePageAccess> findByGroup_IdAndAppModule_IdAndIsAccessibleTrue(String groupId, String appModuleId);

    Optional<GroupModulePageAccess> findByGroup_IdAndAppModule_IdAndPage_Id(String groupId, String appModuleId, String pageId);

    boolean existsByGroup_IdAndAppModule_IdAndPage_Id(String groupId, String appModuleId, String pageId);

    void deleteByGroup_IdAndAppModule_IdAndPage_Id(String groupId, String appModuleId, String pageId);
}
