package com.security.Instituto.service;

import com.security.Instituto.model.Permission;
import com.security.Instituto.repository.IPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService implements IPermissionService{

    @Autowired
    private IPermissionRepository permRepo;

    @Override
    public Permission save(Permission perm) {
        permRepo.save(perm);

        return perm;
    }

    @Override
    public List<Permission> findAll() {
        return permRepo.findAll();
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return permRepo.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        permRepo.deleteById(id);
    }

    @Override
    public Permission update(Permission permission) {
        return this.save(permission);
    }
}
