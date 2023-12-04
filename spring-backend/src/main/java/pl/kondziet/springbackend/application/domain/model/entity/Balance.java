package pl.kondziet.springbackend.application.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.kondziet.springbackend.application.domain.model.id.UserId;

@Data
@AllArgsConstructor
@Builder
public class Balance implements Comparable<Balance> {

    private UserId userId;
    private Money money;

    @Override
    public int compareTo(Balance balance) {
        return money.compareTo(balance.getMoney());
    }
}
