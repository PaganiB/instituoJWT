package com.security.Instituto.controller;


import com.security.Instituto.model.Role;
import com.security.Instituto.model.UserSec;
import com.security.Instituto.service.IRoleService;
import com.security.Instituto.service.IUserSecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserSecService userServ;

    @Autowired
    private IRoleService roleServ;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<UserSec> createUser(@RequestBody UserSec userSec){
        Set<Role> roleList = new HashSet<>();
        Role readRole;

        //se debe encriptar la contraseña
        userSec.setPassword(userServ.encriptPassword(userSec.getPassword()));
        //recuperar el role por id
        for (Role role : userSec.getRolesList()){
            readRole = roleServ.findById(role.getId()).orElseGet(null);
            if(role.getId() != null){
                roleList.add(readRole);
            }
        }
        if(!roleList.isEmpty()){
            userSec.setRolesList(roleList);

            UserSec newUser = userServ.save(userSec);
            return ResponseEntity.ok(newUser);
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<List<UserSec>> getAllUsers(){
        return ResponseEntity.ok(userServ.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserSec> getUserById(@PathVariable Long id){
        Optional<UserSec> userSec = userServ.findById(id);

        return userSec.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/disable/{id}")
    public ResponseEntity<?> disableUser(@PathVariable Long id) {
        try {
            Optional<UserSec> user = userServ.findById(id);

            if (user.isPresent()) {
                UserSec userSec = user.get();
                userSec.setEnabled(false);
                userSec.setAccountNotExpired(false);
                userSec.setAccountNotLocked(false);
                userSec.setCredentialNotExpired(false);
                userServ.save(userSec);
                return ResponseEntity.ok("Usuario desactivado");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al intentar desactivar el usuario");
        }
    }
}
