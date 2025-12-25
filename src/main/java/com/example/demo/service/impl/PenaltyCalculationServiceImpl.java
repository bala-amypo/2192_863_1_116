package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class PenaltyCalculationServiceImpl {

    private ContractRepository contractRepository;
    private DeliveryRecordRepository deliveryRecordRepository;
    private BreachRuleRepository breachRuleRepository;
    private PenaltyCalculationRepository penaltyCalculationRepository;

    public PenaltyCalculation calculatePenalty(Long id) {
        Contract c = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));

        DeliveryRecord dr = deliveryRecordRepository
                .findFirstByContractIdOrderByDeliveryDateDesc(id)
                .orElseThrow(() -> new ResourceNotFoundException("No delivery record"));

        long days = ChronoUnit.DAYS.between(c.getAgreedDeliveryDate(), dr.getDeliveryDate());
        days = Math.max(days, 0);

        BreachRule rule = breachRuleRepository
                .findFirstByActiveTrueOrderByIsDefaultRuleDesc()
                .orElseThrow();

        BigDecimal penalty = rule.getPenaltyPerDay().multiply(BigDecimal.valueOf(days));
        BigDecimal maxCap = c.getBaseContractValue()
                .multiply(BigDecimal.valueOf(rule.getMaxPenaltyPercentage() / 100));

        penalty = penalty.min(maxCap);

        PenaltyCalculation pc = PenaltyCalculation.builder()
                .contract(c)
                .daysDelayed((int) days)
                .calculatedPenalty(penalty)
                .build();

        return penaltyCalculationRepository.save(pc);
    }

    public List<PenaltyCalculation> getCalculationsForContract(Long id) {
        return penaltyCalculationRepository.findByContractId(id);
    }

    public PenaltyCalculation getCalculationById(Long id) {
        return penaltyCalculationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calculation not found"));
    }
}
