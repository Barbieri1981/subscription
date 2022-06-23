package com.sercuirtyserver.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SUBSCRIPTION_USER")
public class SubscriptionUser {

    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", initialValue = 1, allocationSize=1)
    @GeneratedValue(generator = "user_seq")
    @Column(name = "ID_USER", nullable = false)
    private long id;


    @Column(name = "PASSWORD", nullable = false)
    private String password;


    @Column(name = "USER_NAME")
    private String userName;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_USER")
    private List<UserRol> userRols;


}
