package com.org.test.subscription.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subscriptions")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude="users")
@Builder
@Setter
@Getter
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionId;

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "subscriptions")
    private Set<UsersEntity> users = new HashSet<>();

    public SubscriptionEntity(String subscribeName) {
        this.name = subscribeName;
    }
}
