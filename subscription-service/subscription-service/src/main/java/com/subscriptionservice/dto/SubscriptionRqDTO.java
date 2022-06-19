package com.subscriptionservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionRqDTO {

    @NotBlank(message = "email is required")
    @Email
    private String email;
    private String firstName;
    private String gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="GMT-3")
    private Date birthDate;
    private boolean consent;
    private long newsletterId;
}
