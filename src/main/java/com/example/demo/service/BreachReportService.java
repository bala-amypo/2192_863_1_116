package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.Contract;

public interface ContractService {

    Contract save(Contract contract);

    List<Contract> findAll();

    Contract findById(Long id);

    Contract update(Long id, Contract contract);

    void delete(Long id);
}

package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.BreachReport;

public interface BreachReportService {

    BreachReport saveData(BreachReport br);

    List<BreachReport> getAllData();

    BreachReport getById(Long id);

    BreachReport updateData(Long id, BreachReport br);

    void deleteData(Long id);
}