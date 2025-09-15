package br.com.rodrigofolha.delivery.delivery_tracking.infrastructure.http.client;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import br.com.rodrigofolha.delivery.delivery_tracking.domain.service.CourierPayoutCalculationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceHttpImpl implements CourierPayoutCalculationService {

    private final CourierAPIClient courierAPIClient;

    @Override
    public BigDecimal calculatePayout(Double distanceInKm) {
        CourierPayoutResultModel courierPayoutResultModel = courierAPIClient.payoutCalculation(new CourierPayoutCalculationInput(distanceInKm));
        return courierPayoutResultModel.getPayoutFee();
    }
    
}
