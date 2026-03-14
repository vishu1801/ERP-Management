package com.erp.authService.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessiblePageResponseDTO {

    private String pageId;
    private String pageName;
    private String pageDisplayName;
    private String pagePath;
    private String pageIcon;
    private Integer displayOrder;

    @Builder.Default
    private List<AccessiblePageResponseDTO> subPages = new ArrayList<>();
}
