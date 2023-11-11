package pl.kondziet.springbackend.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class Group {

    private GroupId id;
    private String name;
    private String currency;
    private UserId ownerId;

}
