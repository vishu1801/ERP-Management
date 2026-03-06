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
public class AppModuleResponseDTO {

    private String id;
    private String name;
    private String displayName;
    private String description;
    private List<PageResponseDTO> pages = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
