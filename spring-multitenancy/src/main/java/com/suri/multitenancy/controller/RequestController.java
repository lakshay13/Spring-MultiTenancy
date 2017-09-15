package com.suri.multitenancy.controller;

import com.suri.multitenancy.model.Employee;
import com.suri.multitenancy.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/multitenant")
public class RequestController {

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestController.class);

    @RequestMapping(method = RequestMethod.POST, value = "/{tenantId}/{empName}")
    @ResponseBody
    public ResponseEntity process(@PathVariable final String empName) {
        LOGGER.trace("Incoming input");

        // assign the value to the table
        Employee e = new Employee(empName);
        // commit in the database (it is not saved until the transaction is complete or until jpa decides to do so)
        employeeRepository.save(e);

        return new ResponseEntity<String>("Employee " + e + " successfully created", HttpStatus.OK);
    }
}
