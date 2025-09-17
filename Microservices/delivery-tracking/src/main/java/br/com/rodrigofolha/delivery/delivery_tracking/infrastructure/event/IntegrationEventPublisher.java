package br.com.rodrigofolha.delivery.delivery_tracking.infrastructure.event;

public interface IntegrationEventPublisher {
    void publish(Object event, String key, String topic);
}
