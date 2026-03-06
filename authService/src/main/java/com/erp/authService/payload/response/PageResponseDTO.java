package com.erp.authService.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDTO {

    private String id;
    private String name;
    private String displayName;
    private String description;
    private String path;
    private String icon;
    private Integer displayOrder;
    private Boolean isActive;
    private String appModuleId;
    private String appModuleName;
    private String parentId;
    private String parentName;
    private List<PageResponseDTO> subPages = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
