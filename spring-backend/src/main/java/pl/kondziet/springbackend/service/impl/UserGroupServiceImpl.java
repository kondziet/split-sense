package pl.kondziet.springbackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.model.entity.UserGroup;
import pl.kondziet.springbackend.repository.jpa.UserGroupRepository;
import pl.kondziet.springbackend.service.UserGroupService;

@AllArgsConstructor
@Service
public class UserGroupServiceImpl implements UserGroupService {

    private final UserGroupRepository userGroupRepository;

    @Override
    public UserGroup save(UserGroup userGroup) {
        return userGroupRepository.save(userGroup);
    }
}
