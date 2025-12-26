package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.BreachRule;
import com.example.demo.repository.BreachRuleRepository;
import com.example.demo.service.BreachRuleService;

@Service
public class BreachRuleServiceImpl implements BreachRuleService {

    private final BreachRuleRepository repository;

    public BreachRuleServiceImpl(BreachRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public BreachRule save(BreachRule rule) {
        return repository.save(rule);
    }

    @Override
    public List<BreachRule> findAll() {
        return repository.findAll();
    }

    @Override
    public BreachRule findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("BreachRule not found"));
    }

    @Override
    public BreachRule update(Long id, BreachRule rule) {
        BreachRule existing = findById(id);
        existing.setRuleName(rule.getRuleName());
        existing.setPenaltyPerDay(rule.getPenaltyPerDay());
        existing.setMaxPenaltyPercentage(rule.getMaxPenaltyPercentage());
        existing.setActive(rule.getActive());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
