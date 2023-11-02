package pl.kondziet.springbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.model.entity.Expense;

import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
}
