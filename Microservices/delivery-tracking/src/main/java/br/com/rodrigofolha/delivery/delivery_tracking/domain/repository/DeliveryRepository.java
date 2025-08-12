package br.com.rodrigofolha.delivery.delivery_tracking.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodrigofolha.delivery.delivery_tracking.domain.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
    
}
