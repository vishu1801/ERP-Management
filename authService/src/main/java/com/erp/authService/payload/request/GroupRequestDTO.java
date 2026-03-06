package com.erp.authService.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Display name is required")
    private String displayName;

    private String description;

    private Set<String> appModuleIds = new HashSet<>();
}
