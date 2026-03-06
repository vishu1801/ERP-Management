package com.erp.authService.repo;

import com.erp.authService.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends JpaRepository<Page, String> {

    boolean existsByPathAndAppModule_Id(String path, String appModuleId);

    boolean existsByPathAndAppModule_IdAndIdNot(String path, String appModuleId, String id);

    List<Page> findByAppModule_IdAndParentIsNull(String appModuleId);

    List<Page> findByAppModule_Id(String appModuleId);

    List<Page> findByParent_Id(String parentId);

    List<Page> findByAppModule_IdAndIsActiveTrue(String appModuleId);
}
