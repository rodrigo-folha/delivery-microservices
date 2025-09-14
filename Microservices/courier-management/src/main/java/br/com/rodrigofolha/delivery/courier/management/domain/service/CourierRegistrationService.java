package br.com.rodrigofolha.delivery.courier.management.domain.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.rodrigofolha.delivery.courier.management.api.model.CourierInput;
import br.com.rodrigofolha.delivery.courier.management.domain.model.Courier;
import br.com.rodrigofolha.delivery.courier.management.domain.repository.CourierRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourierRegistrationService {

    private final CourierRepository courierRepository;

    public Courier create(CourierInput input) {
        Courier courier = Courier.brandNew(input.getName(), input.getPhone());
        return courierRepository.saveAndFlush(courier);
    }

    public Courier update(UUID courierId, CourierInput input) {
        Courier courier = courierRepository.findById(courierId).orElseThrow();
        courier.setPhone(input.getPhone());
        courier.setName(input.getName());

        return courierRepository.saveAndFlush(courier);
    }
    
}
