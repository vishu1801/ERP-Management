package com.erp.authService.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Display name is required")
    private String displayName;

    private String description;

    @NotBlank(message = "Path is required")
    private String path;

    private String icon;

    private Integer displayOrder;

    private Boolean isActive = true;

    @NotBlank(message = "App module ID is required")
    private String appModuleId;

    private String parentId; // null = root page
}
