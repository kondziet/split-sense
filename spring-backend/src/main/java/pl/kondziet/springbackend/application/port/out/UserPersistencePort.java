package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.application.domain.model.entity.User;

public interface UserPersistencePort {
    User loadUserByEmail(String email);
}
