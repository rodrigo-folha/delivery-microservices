package br.com.rodrigofolha.delivery.courier.management.api.controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.rodrigofolha.delivery.courier.management.api.model.CourierInput;
import br.com.rodrigofolha.delivery.courier.management.api.model.CourierPayoutCalculationInput;
import br.com.rodrigofolha.delivery.courier.management.api.model.CourierPayoutResultModel;
import br.com.rodrigofolha.delivery.courier.management.domain.model.Courier;
import br.com.rodrigofolha.delivery.courier.management.domain.repository.CourierRepository;
import br.com.rodrigofolha.delivery.courier.management.domain.service.CourierPayoutService;
import br.com.rodrigofolha.delivery.courier.management.domain.service.CourierRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/couriers")
@RequiredArgsConstructor
@Slf4j
public class CourierController {
    
    private final CourierRegistrationService courierRegistrationService;
    private final CourierRepository courierRepository;
    private final CourierPayoutService courierPayoutService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Courier create(@Valid @RequestBody CourierInput input) {
        return courierRegistrationService.create(input);
    }

    @PutMapping("/{courierId}")
    public Courier update(@PathVariable UUID courierId, @Valid @RequestBody CourierInput input) {
        return courierRegistrationService.update(courierId, input);
    }

    @GetMapping
    public PagedModel<Courier> findAll(@PageableDefault Pageable pageable) {
        log.info("FindAll Request");
        return new PagedModel<>(courierRepository.findAll(pageable));
    }

    @GetMapping("/{courierId}")
    public Courier findById(@PathVariable UUID courierId) {
        return courierRepository.findById(courierId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/payout-calculation")
    public CourierPayoutResultModel calculate(@RequestBody CourierPayoutCalculationInput input) {
        BigDecimal payoutFee = courierPayoutService.calcule(input.getDistanceInKm());
        return new CourierPayoutResultModel(payoutFee);
    }
    

}
