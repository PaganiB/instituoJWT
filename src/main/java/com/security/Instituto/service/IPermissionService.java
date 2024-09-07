package com.security.Instituto.service;

import com.security.Instituto.model.Permission;

import java.util.List;
import java.util.Optional;

public interface IPermissionService {

    Permission save(Permission perm);
    List<Permission> findAll();
    Optional<Permission> findById(Long id);
    void deleteById(Long id);
    Permission update(Permission permission);
}
