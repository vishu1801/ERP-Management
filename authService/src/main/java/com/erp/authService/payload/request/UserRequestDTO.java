package com.erp.authService.payload.request;

import com.erp.authService.enums.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String groupId;

    @NotNull(message = "User type is required")
    private UserType userType;
}
