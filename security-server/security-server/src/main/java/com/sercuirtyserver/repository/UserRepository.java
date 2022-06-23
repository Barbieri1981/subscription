package com.sercuirtyserver.repository;

import com.sercuirtyserver.entity.SubscriptionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<SubscriptionUser, Long> {
    SubscriptionUser findByUserNameIgnoringCase(String userName);
}
