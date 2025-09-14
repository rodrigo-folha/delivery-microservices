package br.com.rodrigofolha.delivery.delivery_tracking.domain.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rodrigofolha.delivery.delivery_tracking.api.model.ContactPointInput;
import br.com.rodrigofolha.delivery.delivery_tracking.api.model.DeliveryInput;
import br.com.rodrigofolha.delivery.delivery_tracking.api.model.ItemInput;
import br.com.rodrigofolha.delivery.delivery_tracking.domain.exception.DomainException;
import br.com.rodrigofolha.delivery.delivery_tracking.domain.model.ContactPoint;
import br.com.rodrigofolha.delivery.delivery_tracking.domain.model.Delivery;
import br.com.rodrigofolha.delivery.delivery_tracking.domain.model.Delivery.PreparationDetails;
import br.com.rodrigofolha.delivery.delivery_tracking.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryPreparationService {
    
    private final DeliveryRepository deliveryRepository;

    @Transactional
    public Delivery draft(DeliveryInput input) {
        Delivery delivery = Delivery.draft();
        handlePreparation(input, delivery);
        return deliveryRepository.saveAndFlush(delivery);
    }

    @Transactional
    public Delivery edit(UUID deliveryId, DeliveryInput input) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(
            () -> new DomainException());
        delivery.removeItems();
        handlePreparation(input, delivery);
        return deliveryRepository.saveAndFlush(delivery);
    }

    private void handlePreparation(DeliveryInput input, Delivery delivery) {
        ContactPointInput senderInput = input.getSender();
        ContactPointInput recipientInput = input.getRecipient();

        ContactPoint sender = ContactPoint.builder()
            .phone(senderInput.getPhone())
            .name(senderInput.getName())
            .complement(senderInput.getComplement())
            .number(senderInput.getNumber())
            .zipCode(senderInput.getZipCode())
            .street(senderInput.getStreet())
            .build();

        ContactPoint recipient = ContactPoint.builder()
            .phone(recipientInput.getPhone())
            .name(recipientInput.getName())
            .complement(recipientInput.getComplement())
            .number(recipientInput.getNumber())
            .zipCode(recipientInput.getZipCode())
            .street(recipientInput.getStreet())
            .build();

        Duration expectedDeliveryTime = Duration.ofHours(3); // lembrar de mudar depois
        BigDecimal payout = new BigDecimal("10");
        BigDecimal distanceFee = new BigDecimal("10");

        PreparationDetails preparationDetails = Delivery.PreparationDetails.builder()
            .recipient(recipient)
            .sender(sender)
            .expectedDeliveryTime(expectedDeliveryTime)
            .courierPayout(payout)
            .distanceFee(distanceFee)
            .build();

        delivery.editPreparationDetails(preparationDetails);

        for (ItemInput itemInput: input.getItems()) {
            delivery.addItem(itemInput.getName(), itemInput.getQuantity());
        }
    }
}
