package com.subscriptionservice.exception.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetailsDTO {
    private ErrorInfoDTO error;
}
