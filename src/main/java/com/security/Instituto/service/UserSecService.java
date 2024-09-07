package com.security.Instituto.service;

import com.security.Instituto.model.UserSec;
import com.security.Instituto.repository.IUserSecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSecService implements IUserSecService{

    @Autowired
    private IUserSecRepository userRepo;

    @Override
    public List<UserSec> findAll() {
        return userRepo.findAll();
    }

    @Override
    public Optional<UserSec> findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public UserSec save(UserSec userSec) {
        return userRepo.save(userSec);
    }

    @Override
    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public void update(UserSec userSec) {
        save(userSec);
    }

    @Override
    public String encriptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
