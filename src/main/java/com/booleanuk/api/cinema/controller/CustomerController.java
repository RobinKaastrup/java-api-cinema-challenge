package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        customer.setTickets(new ArrayList<Ticket>());
        customer.setCreatedAt(String.valueOf(LocalDateTime.now()));
        customer.setUpdatedAt(customer.getCreatedAt());
        return new ResponseEntity<Customer>(this.repository.save(customer), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Customer> getAll() {
        return this.repository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable int id, @RequestBody Customer customer) {
        Customer customerToUpdate = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setUpdatedAt(String.valueOf(LocalDateTime.now()));

        return new ResponseEntity<Customer>(this.repository.save(customerToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable int id) {
        Customer customerToDelete = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.repository.delete(customerToDelete);
        return ResponseEntity.ok(customerToDelete);
    }

}
