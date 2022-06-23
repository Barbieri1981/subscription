package com.sercuirtyserver.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ROL")
public class Rol {

    @Id
    @SequenceGenerator(name = "ID_ROL_SEQ", sequenceName = "ID_ROL_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "ID_ROL_SEQ")
    @Column(name = "ID_ROL", nullable = false)
    private Long id;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
}
