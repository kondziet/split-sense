package pl.kondziet.springbackend.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.repository.custom.CustomGroupRepository;

import java.util.UUID;

public interface GroupRepository extends JpaRepository<GroupJpaEntity, UUID>, CustomGroupRepository {


}
