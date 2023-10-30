package pl.kondziet.springbackend.util.mapper;

import org.mapstruct.Mapper;
import pl.kondziet.springbackend.model.dto.GroupResponse;
import pl.kondziet.springbackend.model.entity.Group;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupResponse groupToDto(Group group);
}
