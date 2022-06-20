package com.publicservice.sto;

import com.publicservice.enums.GenderType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubscriptionRsSTO {
    private long id;
    private String email;
    private String firstName ;
    private GenderType gender;
    private Date birthDate;
    private boolean consent;
    private long newsletterId;
}
