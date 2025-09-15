package br.com.rodrigofolha.delivery.delivery_tracking.domain.service;

import br.com.rodrigofolha.delivery.delivery_tracking.domain.model.ContactPoint;

public interface DeliveryTimeEsmationService {
    DeliveryEstimate estimate(ContactPoint sender, ContactPoint receiver);
}
