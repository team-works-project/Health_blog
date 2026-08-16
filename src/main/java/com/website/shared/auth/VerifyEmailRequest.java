package com.website.shared.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyEmailRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "\\d{6}", message = "Code must be 6 digits")
    private String code;
}