package com.org.test.subscription.repository;

import com.org.test.subscription.entity.SubscriptionEntity;
import com.org.test.subscription.entity.UsersEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
    boolean existsByName(String subscribeName);

    SubscriptionEntity findByName(String subscribeName);

    @EntityGraph(attributePaths = {"users"})
    List<SubscriptionEntity> findByUsers_UserId(Long usersUserId);

    @Query(value = "SELECT s.* FROM subscriptions s " +
            "LEFT JOIN user_subscription us ON s.subscription_id = us.subscription_id " +
            "GROUP BY s.subscription_id " +
            "ORDER BY COUNT(us.user_id) DESC " +
            "LIMIT 3", nativeQuery = true)
    List<SubscriptionEntity> findTop3ByOrderByUsersSizeDesc();
}
