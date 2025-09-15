package br.com.rodrigofolha.delivery.delivery_tracking.infrastructure.fake;

import java.time.Duration;

import org.springframework.stereotype.Service;

import br.com.rodrigofolha.delivery.delivery_tracking.domain.model.ContactPoint;
import br.com.rodrigofolha.delivery.delivery_tracking.domain.service.DeliveryEstimate;
import br.com.rodrigofolha.delivery.delivery_tracking.domain.service.DeliveryTimeEsmationService;

@Service
public class DeliveryTimeEstimationServiceFake implements DeliveryTimeEsmationService {

    @Override
    public DeliveryEstimate estimate(ContactPoint sender, ContactPoint receiver) {
        return new DeliveryEstimate(Duration.ofHours(3), 3.1);
    }
    
}
