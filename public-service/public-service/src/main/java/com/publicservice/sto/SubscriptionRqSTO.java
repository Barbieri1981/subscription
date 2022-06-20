package com.publicservice.sto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubscriptionRqSTO {
    private String email;
    private Date birthDate;
    private String firstName;
    private String gender;
    private boolean consent;
    private long newsletterId;
}
