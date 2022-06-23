package com.sercuirtyserver.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_ROL")
public class UserRol {
    @Id
    @SequenceGenerator(name = "USER_ROL_SEQ", sequenceName = "USER_ROL_SEQ", initialValue = 1, allocationSize=1)
    @GeneratedValue(generator = "USER_ROL_SEQ")
    @Column(name = "ID_USER_ROL", nullable = false)
    private long id;

    @Column(name = "ID_ROL", nullable = false)
    private long rolId;

    @Column(name = "ID_USER", nullable = false)
    private long userId;
}
