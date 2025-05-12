package com.org.test.subscription.service;

import com.org.test.subscription.dto.SubscriptionDTO;

import java.util.List;

public interface SubscriptionService {

    void subscribe(Long userId, String subscribeName);

    void unsubscribe(Long userId, Long subscribeId);

    List<SubscriptionDTO> getSubscriptions(Long userId);

    List<SubscriptionDTO> getTopPopularSubscriptions();

}
