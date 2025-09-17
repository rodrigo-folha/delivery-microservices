package br.com.rodrigofolha.delivery.delivery_tracking.infrastructure.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.rodrigofolha.delivery.delivery_tracking.domain.event.DeliveryFulfilledEvent;
import br.com.rodrigofolha.delivery.delivery_tracking.domain.event.DeliveryPickUpEvent;
import br.com.rodrigofolha.delivery.delivery_tracking.domain.event.DeliveryPlacedEvent;
import br.com.rodrigofolha.delivery.delivery_tracking.infrastructure.kafka.KafkaTopicConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryDomainEventHandler {

    private final IntegrationEventPublisher integrationEventPublisher;
    
    @EventListener
    public void handle(DeliveryPlacedEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publish(event, event.getDeliveryId().toString(), KafkaTopicConfig.deliveryEventsTopicName);
    }

    @EventListener
    public void handle(DeliveryPickUpEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publish(event, event.getDeliveryId().toString(), KafkaTopicConfig.deliveryEventsTopicName);
    }

    @EventListener
    public void handle(DeliveryFulfilledEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publish(event, event.getDeliveryId().toString(), KafkaTopicConfig.deliveryEventsTopicName);
    }
}
