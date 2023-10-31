package pl.kondziet.springbackend.service;

import pl.kondziet.springbackend.model.entity.User;

public interface UserService {

    User save(User user);
    User findByEmail(String email);
    void updateEmail(User user, String email);
}
