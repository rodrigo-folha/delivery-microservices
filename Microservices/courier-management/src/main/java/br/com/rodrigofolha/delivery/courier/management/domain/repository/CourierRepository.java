package br.com.rodrigofolha.delivery.courier.management.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodrigofolha.delivery.courier.management.domain.model.Courier;

public interface CourierRepository extends JpaRepository<Courier, UUID> {
    
}
