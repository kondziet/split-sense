package pl.kondziet.springbackend.domain.model.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.kondziet.springbackend.domain.model.entity.Money;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class Balance implements Comparable<Balance> {

    private UUID userId;
    private Money money;

    @Override
    public int compareTo(Balance balance) {
        return money.compareTo(balance.getMoney());
    }
}
