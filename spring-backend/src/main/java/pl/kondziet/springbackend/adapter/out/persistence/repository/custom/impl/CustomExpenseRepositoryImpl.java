package pl.kondziet.springbackend.adapter.out.persistence.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.kondziet.springbackend.adapter.out.persistence.entity.*;
import pl.kondziet.springbackend.adapter.out.persistence.repository.custom.CustomExpenseRepository;
import pl.kondziet.springbackend.application.domain.model.entity.GroupExpense;
import pl.kondziet.springbackend.application.domain.model.entity.PersonalExpense;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
@Transactional
public class CustomExpenseRepositoryImpl implements CustomExpenseRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(GroupExpense groupExpense) {
        UserJpaEntity userJpaEntity = em.getReference(UserJpaEntity.class, groupExpense.getPayerId().id());
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
                .payer(userJpaEntity)
                .debtJpaEntities(debtJpaEntities)
                .groupJpaEntity(groupJpaEntity)
                .build();

        em.persist(groupExpenseJpaEntity);
    }

    @Override
    public void save(PersonalExpense personalExpense) {
        UserJpaEntity userJpaEntity = em.getReference(UserJpaEntity.class, personalExpense.getPayerId().id());

        Set<DebtJpaEntity> debtJpaEntities = personalExpense.getDebts().stream()
                .map(debt -> DebtJpaEntity.builder()
                        .debtor(em.getReference(UserJpaEntity.class, debt.getDebtorId().id()))
                        .money(new MoneyJpaEntity(debt.getMoney().getCurrency(), debt.getMoney().getAmount()))
                        .build()
                )
                .collect(Collectors.toSet());

        PersonalExpenseJpaEntity personalExpenseJpaEntity = PersonalExpenseJpaEntity.builder()
                .name(personalExpense.getName())
                .payer(userJpaEntity)
                .debtJpaEntities(debtJpaEntities)
                .build();

        em.persist(personalExpenseJpaEntity);
    }
}
