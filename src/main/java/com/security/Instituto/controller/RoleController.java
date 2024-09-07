package com.security.Instituto.controller;

import com.security.Instituto.model.Permission;
import com.security.Instituto.model.Role;
import com.security.Instituto.service.IPermissionService;
import com.security.Instituto.service.IRoleService;
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
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private IRoleService roleServ;

    @Autowired
    private IPermissionService permissionServ;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Set<Permission> permList = new HashSet<Permission>();
        Permission readPermission;

        //Recuperar el Permission por su ID
        for (Permission per : role.getPermissionsList()) {
            readPermission = permissionServ.findById(per.getId()).orElse(null);
            if (readPermission != null) {
                //si lo encuentra, lo guarda en la lista
                permList.add(readPermission);
            }
        }

        role.setPermissionsList(permList);
        Role newRole = roleServ.save(role);

        return ResponseEntity.ok(newRole);
    }

    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {

        return ResponseEntity.ok(roleServ.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleServ.findById(id);

        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping
    public ResponseEntity<?> updateRolePermissions(@RequestBody Role role) {
        try {
            Set<Permission> permList = role.getPermissionsList();
            Set<Permission> updatePermList = new HashSet<>();

            Permission readPermission;
            //agregar permisos actualizados
            for (Permission per : permList) {
                readPermission = permissionServ.findById(per.getId()).orElseGet(null);
                if (readPermission != null) {
                    //si lo encuentra, lo guarda
                    permList.add(readPermission);
                }
            }
            role.setPermissionsList(updatePermList);
            roleServ.update(role);
            return ResponseEntity.ok("Permisos actualizados");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error actualizando permisos");
        }
    }

}

