package pl.kondziet.springbackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "EXPENSE_DEBTORS")
public class ExpenseDebtor implements Serializable {

    @Data
    @Embeddable
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExpenseDebtorId implements Serializable{
        private UUID expenseId;
        private UUID debtorId;
    }

    @EmbeddedId
    private ExpenseDebtorId id;
    @ManyToOne
    @MapsId(value = "expenseId")
    private Expense expense;
    @ManyToOne
    @MapsId(value = "debtorId")
    private User debtor;
    private String currency;
    private BigDecimal amount;
}
