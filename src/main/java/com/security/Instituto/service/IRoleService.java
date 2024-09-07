package com.security.Instituto.service;

import com.security.Instituto.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService{

    Role save(Role role);
    Optional<Role> findById(Long id);
    void deleteById(Long id);
    List<Role> findAll();
    Role update (Role role);
}
