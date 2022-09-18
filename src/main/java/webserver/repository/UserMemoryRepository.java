package webserver.repository;

import model.User;
import webserver.exception.UserAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserMemoryRepository implements UserRepository {
    private static Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        String id = user.getUserId();

        if (users.containsKey(id)) {
            throw new UserAlreadyExistsException();
        }

        users.put(id, user);
        return user;
    }

    @Override
    public Optional<User> findByUserId(String id) {
        if (users.containsKey(id)) {
            return Optional.of(users.get(id));
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> update(User user) {
        String id = user.getUserId();

        if (!users.containsKey(id)) {
            return Optional.empty();
        }

        users.put(id, user);
        return Optional.of(user);
    }

    @Override
    public boolean delete(User user) {
        String id = user.getUserId();

        if (users.containsKey(id)) {
            users.remove(id);
            return true;
        }

        return false;
    }

    @Override
    public void clear() {
        users.clear();
    }
}
