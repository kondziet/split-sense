package pl.kondziet.springbackend.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.domain.model.entity.GroupMembership;
import pl.kondziet.springbackend.infrastructure.persistence.GroupMembershipRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class GroupMembershipService {

    private final GroupMembershipRepository groupMembershipRepository;

    public GroupMembership save(GroupMembership groupMembership) {
        return groupMembershipRepository.save(groupMembership);
    }
}
