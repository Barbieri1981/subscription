package com.subscriptionservice.sto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailRqSTO {
    private String email;
    private String subject;
    private String message;
}
