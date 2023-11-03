package pl.kondziet.springbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.model.entity.Debt;

import java.util.UUID;

public interface DebtRepository extends JpaRepository<Debt, UUID> {
}
