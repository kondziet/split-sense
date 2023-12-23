package pl.kondziet.springbackend.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.domain.model.entity.Expense;
import pl.kondziet.springbackend.domain.model.entity.GroupExpense;

import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    List<GroupExpense> findAllByGroup_Id(UUID id);
}
