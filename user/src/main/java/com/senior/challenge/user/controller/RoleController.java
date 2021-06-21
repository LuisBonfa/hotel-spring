package com.senior.challenge.user.controller;

import com.senior.challenge.user.dto.RoleDTO;
import com.senior.challenge.user.entity.Role;
import com.senior.challenge.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.save(Role.create(roleDTO)));
    }
}
