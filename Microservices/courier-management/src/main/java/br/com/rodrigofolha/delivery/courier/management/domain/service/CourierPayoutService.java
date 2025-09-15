package br.com.rodrigofolha.delivery.courier.management.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

@Service
public class CourierPayoutService {

    public BigDecimal calcule(Double distanceInKm) {
        return new BigDecimal("10").multiply(new BigDecimal(distanceInKm)).setScale(2, RoundingMode.HALF_EVEN);
    }
    
}
