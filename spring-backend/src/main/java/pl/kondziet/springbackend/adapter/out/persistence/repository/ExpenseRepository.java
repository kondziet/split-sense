package pl.kondziet.springbackend.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.adapter.out.persistence.entity.ExpenseJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupExpenseJpaEntity;

import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<ExpenseJpaEntity, UUID> {
    List<GroupExpenseJpaEntity> findAllByGroupJpaEntity_Id(UUID id);
}
