package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.application.domain.model.User;
import pl.kondziet.springbackend.application.domain.model.UserId;

public interface LoadUserPort {

    User loadUser(UserId userId);
}
