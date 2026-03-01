package com.erp.authService.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppModuleResponseDTO {

    private String id;
    private String name;
    private String description;
    private List<GroupResponseDTO> groups;
}
