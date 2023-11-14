package pl.kondziet.springbackend.application.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.kondziet.springbackend.application.domain.model.id.UserId;

@Data
@AllArgsConstructor
@Builder
public class User {

    private UserId id;
    private String username;
    private String email;

}
