package com.subscriptionservice.entity;

import com.subscriptionservice.enums.GenderType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "SUBSCRIPTION")
public class Subscription {

    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", initialValue = 1, allocationSize=1)
    @GeneratedValue(generator = "user_seq")
    @Column(name = "ID_SUBSCRIPTION", nullable = false)
    private long id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "FIRST_NAME")
    private String firstName ;

    @Column(name = "GENDER")
    private GenderType gender;

    @Column(name = "BIRTH_DATE", nullable = false)
    private Date birthDate;

    @Column(name = "CONSENT", nullable = false)
    private boolean consent;

    @Column(name = "ID_NEWSLETTER", nullable = false)
    private long newsletterId;

}
