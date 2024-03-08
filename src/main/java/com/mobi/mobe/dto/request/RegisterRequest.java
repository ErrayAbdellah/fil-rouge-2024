package com.mobi.mobe.dto.request;

import com.mobi.mobe.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String bio;
    private Gender gender;
    private String profilePictureUrl;
//    private Long roleId; // Assuming Role has an 'id' field:
}
