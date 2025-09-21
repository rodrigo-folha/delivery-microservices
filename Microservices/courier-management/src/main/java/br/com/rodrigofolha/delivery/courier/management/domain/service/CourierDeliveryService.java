package br.com.rodrigofolha.delivery.courier.management.domain.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.rodrigofolha.delivery.courier.management.domain.model.Courier;
import br.com.rodrigofolha.delivery.courier.management.domain.repository.CourierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CourierDeliveryService {
    
    private final CourierRepository courierRepository;

    public void assign(UUID deliveryId) {
        Courier courier = courierRepository.findTop1ByOrderByLastFulfilledDeliveryAtAsc()
                    .orElseThrow();

        courier.assign(deliveryId);
        courierRepository.saveAndFlush(courier);
        log.info("Courier {} assigned to delivery {}", courier.getId(), deliveryId);
    }

    public void fulfill(UUID deliveryId) {
        Courier courier = courierRepository.findByPendingDeliveries_id(deliveryId).orElseThrow();
        courier.fulfill(deliveryId);
        courierRepository.saveAndFlush(courier);
        log.info("Courier {} fulfilled the delivery {}", courier.getId(), deliveryId);
    }
}
