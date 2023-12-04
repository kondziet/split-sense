package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.domain.model.entity.User;

public interface UserPersistencePort {
    User loadUserByEmail(String email);
}
