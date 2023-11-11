package pl.kondziet.springbackend.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User {

    private UserId id;
    private String username;
    private String email;

}
