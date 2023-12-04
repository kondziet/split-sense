package pl.kondziet.springbackend.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.kondziet.springbackend.domain.model.valueobjects.GroupId;
import pl.kondziet.springbackend.domain.model.valueobjects.UserId;

@Data
@AllArgsConstructor
@Builder
public class Group {

    private GroupId id;
    private String name;
    private String currency;
    private UserId ownerId;

}
