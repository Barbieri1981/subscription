package com.emailservice.Exception.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetailsDTO {
    private ErrorInfoDTO error;
}
