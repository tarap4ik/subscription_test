package com.org.test.subscription.mapper;

import com.org.test.subscription.dto.SubscriptionDTO;
import com.org.test.subscription.entity.SubscriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);

    SubscriptionDTO subscriptionToSubscriptionDTO(SubscriptionEntity subscription);

    List<SubscriptionDTO> subscriptionsToSubscriptionDTOs(List<SubscriptionEntity> subscriptions);

}
