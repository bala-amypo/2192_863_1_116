package com.example.demo.controller;

import com.example.demo.entity.DeliveryRecord;
import com.example.demo.service.DeliveryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-records")
public class DeliveryRecordController {

    private final DeliveryRecordService service;

    @Autowired
    public DeliveryRecordController(DeliveryRecordService service) {
        this.service = service;
    }

    @PostMapping
    public DeliveryRecord create(@RequestBody DeliveryRecord deliveryRecord) {
        return service.save(deliveryRecord);
    }

    @GetMapping
    public List<DeliveryRecord> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public DeliveryRecord getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public DeliveryRecord update(@PathVariable Long id, @RequestBody DeliveryRecord deliveryRecord) {
        return service.update(id, deliveryRecord);
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

import com.example.demo.entity.BreachRule;
import com.example.demo.service.BreachRuleService;

@RestController
@RequestMapping("/breach-rule")
public class BreachRuleController {

    @Autowired
    private BreachRuleService service;

    @PostMapping("/post")
    public BreachRule create(@RequestBody BreachRule rule) {
        return service.save(rule);
    }

    @GetMapping("/get")
    public List<BreachRule> getAll() {
        return service.findAll();
    }

    @GetMapping("/get/{id}")
    public BreachRule getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/update/{id}")
    public BreachRule update(
            @PathVariable Long id,
            @RequestBody BreachRule rule) {
        return service.update(id, rule);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "BreachRule with ID " + id + " deleted successfully!";
    }
}

