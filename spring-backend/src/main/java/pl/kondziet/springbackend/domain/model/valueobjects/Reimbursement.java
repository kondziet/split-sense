package pl.kondziet.springbackend.domain.model.valueobjects;

import lombok.Builder;
import pl.kondziet.springbackend.domain.model.entity.Money;

import java.util.UUID;

@Builder
public record Reimbursement(UUID debtorId, UUID creditorId, Money money) {
}
