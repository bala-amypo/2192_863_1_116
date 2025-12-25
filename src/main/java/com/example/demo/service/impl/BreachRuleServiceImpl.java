package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import java.math.BigDecimal;
import java.util.*;

public class BreachRuleServiceImpl {

    private BreachRuleRepository breachRuleRepository;

    public BreachRule createRule(BreachRule r) {
        if (r.getPenaltyPerDay().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException();
        if (r.getMaxPenaltyPercentage() > 100)
            throw new IllegalArgumentException();
        return breachRuleRepository.save(r);
    }

    public void deactivateRule(Long id) {
        BreachRule r = breachRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));
        r.setActive(false);
        breachRuleRepository.save(r);
    }

    public BreachRule getActiveDefaultOrFirst() {
        return breachRuleRepository
                .findFirstByActiveTrueOrderByIsDefaultRuleDesc()
                .orElseThrow(() -> new ResourceNotFoundException("No active breach rule"));
    }

    public List<BreachRule> getAllRules() {
        return breachRuleRepository.findAll();
    }
}
