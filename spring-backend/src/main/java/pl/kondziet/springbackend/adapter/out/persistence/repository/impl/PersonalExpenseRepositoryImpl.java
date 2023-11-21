package pl.kondziet.springbackend.adapter.out.persistence.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.kondziet.springbackend.adapter.out.persistence.entity.DebtJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.entity.MoneyJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.entity.PersonalExpenseJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.repository.PersonalExpenseRepository;
import pl.kondziet.springbackend.application.domain.model.entity.PersonalExpense;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
@Transactional
public class PersonalExpenseRepositoryImpl implements PersonalExpenseRepository {

    @PersistenceContext
    private EntityManager em;
    @Override
    public void save(PersonalExpense personalExpense) {
        UserJpaEntity userJpaEntity = em.getReference(UserJpaEntity.class, personalExpense.getCreditorId().id());

        Set<DebtJpaEntity> debtJpaEntities = personalExpense.getDebts().stream()
                .map(debt -> DebtJpaEntity.builder()
                        .debtor(em.getReference(UserJpaEntity.class, debt.getDebtorId().id()))
                        .money(new MoneyJpaEntity(debt.getMoney().getCurrency(), debt.getMoney().getAmount()))
                        .build()
                )
                .collect(Collectors.toSet());

        PersonalExpenseJpaEntity personalExpenseJpaEntity = PersonalExpenseJpaEntity.builder()
                .name(personalExpense.getName())
                .creditor(userJpaEntity)
                .debtJpaEntities(debtJpaEntities)
                .build();

        em.persist(personalExpenseJpaEntity);
    }
}
