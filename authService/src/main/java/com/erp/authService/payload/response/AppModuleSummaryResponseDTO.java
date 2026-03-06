package com.erp.authService.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppModuleSummaryResponseDTO {

    private String id;
    private String name;
    private String displayName;
    private String description;
}
