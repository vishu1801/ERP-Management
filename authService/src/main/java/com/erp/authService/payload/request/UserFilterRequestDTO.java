package com.erp.authService.payload.request;

import com.erp.authService.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFilterRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private UserType userType;
    private String groupId;
}
