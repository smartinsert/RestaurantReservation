package main.java.services;

import main.java.domain.User;

import java.util.UUID;

public interface UserService {
    User addUser(User user);
    String getUserName(UUID userId);
}
