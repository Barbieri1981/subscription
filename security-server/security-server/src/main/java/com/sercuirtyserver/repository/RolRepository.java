package com.sercuirtyserver.repository;


import com.sercuirtyserver.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    public Optional<Rol> findByDescriptionIgnoringCase(final String rolName);
}
