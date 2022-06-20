package com.emailservice.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRqDTO {

    @NotBlank(message = "email is required")
    @Email
    private String email;

    @NotBlank(message = "subject is required")
    private String subject;

    @NotBlank(message = "message is required")
    private String message;
}
