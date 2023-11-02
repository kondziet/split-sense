package pl.kondziet.springbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.model.entity.ExpenseDebtor;

import java.util.UUID;

public interface ExpenseDebtorRepository extends JpaRepository<ExpenseDebtor, UUID> {
}
