package com.security.Instituto.controller;

import com.security.Instituto.model.Permission;
import com.security.Instituto.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private IPermissionService permissionServ;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Permission> createPermission(@RequestBody Permission perm){
            Permission newPermission = permissionServ.save(perm);

            return ResponseEntity.ok(newPermission);
    }

    @GetMapping
    public ResponseEntity<List<Permission>> getAllPermission(){
        List<Permission> permissionList = permissionServ.findAll();

        return ResponseEntity.ok(permissionList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermission(@PathVariable Long id){
        Optional<Permission> permission = permissionServ.findById(id);

        return permission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePermission(@PathVariable Long id){
        try{
            permissionServ.deleteById(id);
            return ResponseEntity.ok("Permiso borrado");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error tratando de borrar el permiso");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping()
    public ResponseEntity<?> updatePermission(@RequestBody Permission permission){
            try{
                permissionServ.save(permission);
                return ResponseEntity.ok("Permisos actualizados");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error actualizando permiso");
            }
    }


}
