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
public class GroupModulePageAccessRequestDTO {

    @NotBlank(message = "Group ID is required")
    private String groupId;

    @NotBlank(message = "App module ID is required")
    private String appModuleId;

    @NotBlank(message = "Page ID is required")
    private String pageId;

    private Boolean isAccessible = true;
}
