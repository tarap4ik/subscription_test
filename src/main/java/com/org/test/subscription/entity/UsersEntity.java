package com.org.test.subscription.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Setter
@Getter
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "user_subscription",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id")
    )
    private Set<SubscriptionEntity> subscriptions = new HashSet<>();


    public void subscribe(SubscriptionEntity subscription) {
        this.subscriptions.add(subscription);
        subscription.getUsers().add(this);
    }

    public void unsubscribe(SubscriptionEntity subscription) {
        this.subscriptions.remove(subscription);
        subscription.getUsers().remove(this);
    }
}

