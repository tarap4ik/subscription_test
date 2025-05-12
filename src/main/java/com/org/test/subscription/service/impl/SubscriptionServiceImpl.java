package com.org.test.subscription.service.impl;

import com.org.test.subscription.dto.SubscriptionDTO;
import com.org.test.subscription.entity.SubscriptionEntity;
import com.org.test.subscription.exception.NotFoundSubscriptionException;
import com.org.test.subscription.exception.NotFoundUserException;
import com.org.test.subscription.mapper.SubscriptionMapper;
import com.org.test.subscription.repository.SubscriptionRepository;
import com.org.test.subscription.repository.UsersRepository;
import com.org.test.subscription.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UsersRepository usersRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, UsersRepository usersRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public void subscribe(Long userId, String subscribeName) {
        SubscriptionEntity subscriptionEntity = subscriptionRepository.findByName(subscribeName);
        var user = usersRepository.findById(userId).orElseThrow(() -> new NotFoundUserException(String.valueOf(userId)));
        if (subscriptionEntity == null) {
            subscriptionEntity = subscriptionRepository.save(new SubscriptionEntity(subscribeName));
            log.info("Новая подписка добавлена: {}", subscriptionEntity.getSubscriptionId());
        } else {
            subscriptionEntity = subscriptionRepository.findByName(subscribeName);
            if (subscriptionEntity.getUsers().contains(user)){
                log.info("Подписка для этого пользователя уже есть");
                return;
            }
        }
        user.subscribe(subscriptionEntity);
        usersRepository.save(user);
        log.info("Подписка {} для пользователя {} добавлена", subscriptionEntity.getName(), user.getUserId());
    }

    @Override
    public void unsubscribe(Long userId, Long subscribeId) {
        var subscriptionEntity = subscriptionRepository.findById(subscribeId).orElseThrow(
                () -> new NotFoundSubscriptionException(String.valueOf(subscribeId))
        );
        var user = usersRepository.findById(userId).orElseThrow(
                () -> new NotFoundUserException(String.valueOf(userId))
        );
        if (!subscriptionEntity.getUsers().contains(user)){
            log.info("Подписка для этого пользователя уже отсутствует");
            return;
        }
        user.unsubscribe(subscriptionEntity);
        usersRepository.save(user);
        log.info("Подписка {} для пользователя {} удалена", subscriptionEntity.getName(), user.getUserId());
    }

    @Override
    public List<SubscriptionDTO> getSubscriptions(Long userId) {
        var listSubscription = subscriptionRepository.findByUsers_UserId(userId);
        var subscribe = SubscriptionMapper.INSTANCE.subscriptionsToSubscriptionDTOs(listSubscription);
        return subscribe;
    }

    @Override
    public List<SubscriptionDTO> getTopPopularSubscriptions() {
        var  listSubscription = subscriptionRepository.findTop3ByOrderByUsersSizeDesc();
        var subscribe = SubscriptionMapper.INSTANCE.subscriptionsToSubscriptionDTOs(listSubscription);
        return subscribe;
    }
}
