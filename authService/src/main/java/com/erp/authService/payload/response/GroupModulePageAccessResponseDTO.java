package com.erp.authService.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupModulePageAccessResponseDTO {

    private String id;
    private String groupId;
    private String groupName;
    private String appModuleId;
    private String appModuleName;
    private String pageId;
    private String pageName;
    private String pagePath;
    private Boolean isAccessible;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
