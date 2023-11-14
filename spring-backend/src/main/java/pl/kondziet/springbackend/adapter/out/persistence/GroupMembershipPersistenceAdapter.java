package pl.kondziet.springbackend.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.adapter.out.persistence.repository.GroupMembershipRepository;
import pl.kondziet.springbackend.application.domain.model.entity.GroupMembership;
import pl.kondziet.springbackend.application.port.out.SaveGroupMembershipPort;

@Component
@RequiredArgsConstructor
public class GroupMembershipPersistenceAdapter implements SaveGroupMembershipPort {

    private final GroupMembershipRepository groupMembershipRepository;

    @Override
    public void saveGroupMembership(GroupMembership groupMembership) {
        groupMembershipRepository.save(groupMembership);
    }
}
