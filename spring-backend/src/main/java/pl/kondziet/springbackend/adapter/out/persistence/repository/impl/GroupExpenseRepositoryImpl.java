package pl.kondziet.springbackend.adapter.out.persistence.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.kondziet.springbackend.adapter.out.persistence.entity.*;
import pl.kondziet.springbackend.adapter.out.persistence.repository.GroupExpenseRepository;
import pl.kondziet.springbackend.application.domain.model.entity.GroupExpense;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;
import pl.kondziet.springbackend.infrastructure.mapper.ExpenseMapper;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
@Transactional
public class GroupExpenseRepositoryImpl implements GroupExpenseRepository {

    @PersistenceContext
    private EntityManager em;
    private final ExpenseMapper expenseMapper;
    @Override
    public void save(GroupExpense groupExpense) {
        UserJpaEntity userJpaEntity = em.getReference(UserJpaEntity.class, groupExpense.getCreditorId().id());
        GroupJpaEntity groupJpaEntity = em.getReference(GroupJpaEntity.class, groupExpense.getGroupId().id());

        Set<DebtJpaEntity> debtJpaEntities = groupExpense.getDebts().stream()
                .map(debt -> DebtJpaEntity.builder()
                        .debtor(em.getReference(UserJpaEntity.class, debt.getDebtorId().id()))
                        .money(new MoneyJpaEntity(debt.getMoney().getCurrency(), debt.getMoney().getAmount()))
                        .build()
                )
                .collect(Collectors.toSet());

        GroupExpenseJpaEntity groupExpenseJpaEntity = GroupExpenseJpaEntity.builder()
                .name(groupExpense.getName())
                .creditor(userJpaEntity)
                .debtJpaEntities(debtJpaEntities)
                .groupJpaEntity(groupJpaEntity)
                .build();

        em.persist(groupExpenseJpaEntity);
    }

    @Override
    public List<GroupExpense> findAllExpenses(GroupId groupId) {
        String jpql = """
                SELECT ge
                FROM GroupExpenseJpaEntity ge
                WHERE ge.groupJpaEntity.id = :groupId
                """;
        TypedQuery<GroupExpenseJpaEntity> query = em.createQuery(jpql, GroupExpenseJpaEntity.class);

        UUID uuid = groupId.id();
        query.setParameter("groupId", uuid);

        List<GroupExpenseJpaEntity> resultList = query.getResultList();
        return expenseMapper.groupExpenseJpaEntitiesToGroupExpenses(resultList);
    }
}
