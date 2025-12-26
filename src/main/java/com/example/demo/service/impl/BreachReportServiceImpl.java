package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.BreachReport;
import com.example.demo.repository.BreachReportRepository;
import com.example.demo.service.BreachReportService;

@Service
public class BreachReportServiceImpl implements BreachReportService {

    private final BreachReportRepository repository;

    public BreachReportServiceImpl(BreachReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public BreachReport saveData(BreachReport br) {
        return repository.save(br);
    }

    @Override
    public List<BreachReport> getAllData() {
        return repository.findAll();
    }

    @Override
    public BreachReport getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("BreachReport not found"));
    }

    @Override
    public BreachReport updateData(Long id, BreachReport br) {
        BreachReport existing = getById(id);
        existing.setDaysDelayed(br.getDaysDelayed());
        existing.setRemark(br.getRemark());
        return repository.save(existing);
    }

    @Override
    public void deleteData(Long id) {
        repository.deleteById(id);
    }
}
