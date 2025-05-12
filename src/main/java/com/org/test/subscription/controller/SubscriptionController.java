package com.org.test.subscription.controller;

import com.org.test.subscription.dto.SubscriptionDTO;
import com.org.test.subscription.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Subscription API")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Operation(summary = "Подписаться на пользователя", description = "Добавить подписку пользователю по id")
    @PostMapping("/users/{id}/subscriptions")
    public void subscribe(@PathVariable @Min(1) Long id, @RequestParam String subscribeName) {
        subscriptionService.subscribe(id, subscribeName);
    }

    @Operation(summary = "Получить подписки пользователя", description = "Получить подписки пользователя по id")
    @GetMapping("/users/{id}/subscriptions")
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptions(@PathVariable @Min(1) Long id) {
        var listSubs = subscriptionService.getSubscriptions(id);
        if (listSubs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listSubs);
    }

    @Operation(summary = "Удалить подписку у пользователя", description = "Удалить подписку по sub_id у пользователя по его id")
    @DeleteMapping("/users/{id}/subscriptions/{sub_id}")
    public void unsubscribe(@PathVariable @Min(1) Long id, @PathVariable @Min(1) Long sub_id) {
        subscriptionService.unsubscribe(id, sub_id);
    }

    @Operation(summary = "Топ", description = "Топ 3 популярные подписики")
    @GetMapping("/subscriptions/top")
    public ResponseEntity<List<SubscriptionDTO>> getTopSubscriptions() {
        var topList = subscriptionService.getTopPopularSubscriptions();
        if (topList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(topList);
    }

}
