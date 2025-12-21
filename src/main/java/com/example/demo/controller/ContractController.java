package com.example.demo.controller;

import com.example.demo.entity.PenaltyCalculation;
import com.example.demo.service.PenaltyCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/penalties")
public class PenaltyCalculationController {

    private final PenaltyCalculationService service;

    @Autowired
    public PenaltyCalculationController(PenaltyCalculationService service) {
        this.service = service;
    }

    @PostMapping
    public PenaltyCalculation create(@RequestBody PenaltyCalculation penaltyCalculation) {
        return service.save(penaltyCalculation);
    }

    @GetMapping
    public List<PenaltyCalculation> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PenaltyCalculation getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public PenaltyCalculation update(@PathVariable Long id, @RequestBody PenaltyCalculation penaltyCalculation) {
        return service.update(id, penaltyCalculation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Contract;
import com.example.demo.service.ContractService;

@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private ContractService service;

    @PostMapping("/post")
    public Contract create(@RequestBody Contract contract) {
        return service.save(contract);
    }

    @GetMapping("/get")
    public List<Contract> getAll() {
        return service.findAll();
    }

    @GetMapping("/get/{id}")
    public Contract getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/update/{id}")
    public Contract update(
            @PathVariable Long id,
            @RequestBody Contract contract) {
        return service.update(id, contract);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Contract with ID " + id + " deleted successfully!";
    }
}