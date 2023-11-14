package pl.kondziet.springbackend.application.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.kondziet.springbackend.application.domain.model.id.UserId;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class Debt {

    private UserId debtorId;
    private String currency;
    private BigDecimal amount;
}
