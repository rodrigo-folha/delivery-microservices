package br.com.rodrigofolha.delivery.delivery_tracking.api.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierIdInput {
    private UUID courierID;
}
