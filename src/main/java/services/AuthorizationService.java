package main.java.services;

import main.java.domain.User;

public interface AuthorizationService {
    boolean login(int userId);
    boolean register(User user);
}
