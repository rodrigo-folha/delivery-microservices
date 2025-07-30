package br.com.rodrigofolha.delivery.delivery_tracking.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Duration;

import org.junit.jupiter.api.Test;

import br.com.rodrigofolha.delivery.delivery_tracking.domain.exception.DomainException;
import br.com.rodrigofolha.delivery.delivery_tracking.domain.model.Delivery.PreparationDetails;

public class DeliveryTest {

    @Test
    public void shouldChangeToPlaced() {
        Delivery delivery = Delivery.draft();

        delivery.editPreparationDetails(createdValidPreparationDetails());
        delivery.place();

        assertEquals(DeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus());
        assertNotNull(delivery.getPlacedAt());
    }

    @Test
    public void shouldNotPlace() {
        Delivery delivery = Delivery.draft();

        assertThrows(DomainException.class, () -> {
            delivery.place();
        });

        assertEquals(DeliveryStatus.DRAFT, delivery.getStatus());
        assertNull(delivery.getPlacedAt());
        
    }

    private PreparationDetails createdValidPreparationDetails() {
        ContactPoint sender = ContactPoint.builder()
            .zipCode("00000-000")
            .street("Rua São Paulo")
            .number("100")
            .complement("Sala 401")
            .name("João Silva")
            .phone("(11) 90000-1234")
            .build();

        ContactPoint recipient = ContactPoint.builder()
            .zipCode("12345-789")
            .street("Rua Brasil")
            .number("500")
            .complement("")
            .name("Maria Silva")
            .phone("(11) 91234-4321")
            .build();

        return Delivery.PreparationDetails.builder()
            .sender(sender)
            .recipient(recipient)
            .distanceFee(new BigDecimal("15.00"))
            .courierPayout(new BigDecimal("5.00"))
            .expectedDeliveryTime(Duration.ofHours(5))
            .build();
    }
}
