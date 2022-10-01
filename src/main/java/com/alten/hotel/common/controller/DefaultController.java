package com.alten.hotel.common.controller;

import com.alten.hotel.common.exception.exceptions.GenericException;
import com.alten.hotel.common.service.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

/**
 * This abstract class is used to
 * optimize the creations of controllers, when you extend it
 * you already have all the endpoints listed below
 * [FindAll, FindOne, Save, Update, Delete]
 *
 * @param <D> the DTO that it's going to be used
 * @param <R> the Resource that it's going to be used
 * @author luis.bonfa
 */
public abstract class DefaultController<D, R> {

    public DefaultService<D, R> service;

    @Autowired
    public void setService(DefaultService<D, R> service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(UUID.fromString(id)));
    }

    @PostMapping
    @ExceptionHandler(value = GenericException.class)
    public ResponseEntity<?> save(@Validated @RequestBody D data) {
        return ResponseEntity.ok(service.save(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @Validated @RequestBody D data) {
        return ResponseEntity.ok(service.update(UUID.fromString(id), data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return ResponseEntity.ok(service.delete(UUID.fromString(id)));
    }
}
