package main.java.services;

import main.java.domain.User;
import main.java.utils.CommonUtility;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserServiceInMemoryImpl implements UserService {
    private Map<UUID, User> userIdToUser;

    public UserServiceInMemoryImpl() {
        userIdToUser = new HashMap<>();
    }

    @Override
    public User addUser(User user) {
        return userIdToUser.putIfAbsent(CommonUtility.generateId(), user);
    }

    @Override
    public String getUserName(UUID userId) {
        return userIdToUser.containsKey(userId) ? userIdToUser.get(userId).getName() : "";
    }

}
