package pl.kondziet.springbackend.util.mapper;

import org.mapstruct.Mapper;
import pl.kondziet.springbackend.model.dto.GroupRequest;
import pl.kondziet.springbackend.model.dto.GroupResponse;
import pl.kondziet.springbackend.model.entity.Group;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupResponse groupToDto(Group group);
    Group dtoToGroup(GroupRequest groupRequest);
    List<GroupResponse> groupsToDtos(List<Group> groups);
}
