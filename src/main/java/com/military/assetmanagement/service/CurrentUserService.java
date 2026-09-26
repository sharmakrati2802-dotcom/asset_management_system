package com.military.assetmanagement.service;

import com.military.assetmanagement.entity.User;
import com.military.assetmanagement.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    private final UserRepository repository;
    public CurrentUserService(UserRepository repository) { this.repository = repository; }

    public User get() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return repository.findByUsername(username).orElseThrow();
    }

    public void checkBaseAccess(Long baseId) {
        User user = get();
        if (user.getRole().name().equals("ADMIN")) return;
        if (user.getBase() == null || !user.getBase().getId().equals(baseId)) {
            throw new SecurityException("You are not authorized for this base");
        }
    }
}
