package pl.kondziet.springbackend.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.application.domain.model.entity.User;
import pl.kondziet.springbackend.application.domain.model.id.UserId;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

    List<User> userJpaEntityToUsers(List<UserJpaEntity> userJpaEntities);
    @Mapping(target = "id", source = "userJpaEntity.id", qualifiedByName = "idToUserId")
    User userJpaEntityToUser(UserJpaEntity userJpaEntity);
    @Named("idToUserId")
    default UserId idToUserId(UUID id) {
        return new UserId(id);
    }
}
