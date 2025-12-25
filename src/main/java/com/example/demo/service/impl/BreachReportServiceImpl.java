package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import java.util.*;

public class BreachReportServiceImpl {

    private BreachReportRepository breachReportRepository;
    private PenaltyCalculationRepository penaltyCalculationRepository;
    private ContractRepository contractRepository;

    public BreachReport generateReport(Long id) {
        Contract c = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));

        PenaltyCalculation pc = penaltyCalculationRepository
                .findTopByContractIdOrderByCalculatedAtDesc(id)
                .orElseThrow(() -> new ResourceNotFoundException("No penalty calculation"));

        BreachReport r = BreachReport.builder()
                .contract(c)
                .daysDelayed(pc.getDaysDelayed())
                .penaltyAmount(pc.getCalculatedPenalty())
                .build();

        return breachReportRepository.save(r);
    }

    public List<BreachReport> getReportsForContract(Long id) {
        return breachReportRepository.findByContractId(id);
    }

    public List<BreachReport> getAllReports() {
        return breachReportRepository.findAll();
    }
}
