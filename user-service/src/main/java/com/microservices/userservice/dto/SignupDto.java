package com.microservices.userservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    @NotBlank
    @Size(min = 4,max = 50)
    private String name;

    @Email(message = "Invalid email format!")
    @NotEmpty(message = "Email is required!")
    @Size(max = 50)
    private String email;

    @NotEmpty(message = "Phone number is required!")
    @Size(min = 8,max = 50)
    private String phoneNumber;

    @NotEmpty(message = "Password is required")
    @Size(min = 8,max= 50)
    private String password;

}
