package pl.kondziet.springbackend.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.adapter.out.persistence.repository.GroupMembershipRepository;
import pl.kondziet.springbackend.application.domain.model.entity.GroupMembership;
import pl.kondziet.springbackend.application.port.out.GroupMembershipOutputPort;

@Component
@RequiredArgsConstructor
public class GroupMembershipPersistenceAdapterOutput implements GroupMembershipOutputPort {

    private final GroupMembershipRepository groupMembershipRepository;

    @Override
    public void saveGroupMembership(GroupMembership groupMembership) {
        groupMembershipRepository.save(groupMembership);
    }
}
