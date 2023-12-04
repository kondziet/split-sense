package pl.kondziet.springbackend.domain.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import pl.kondziet.springbackend.domain.model.valueobjects.GroupId;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class GroupExpense extends Expense {

    private GroupId groupId;
}
