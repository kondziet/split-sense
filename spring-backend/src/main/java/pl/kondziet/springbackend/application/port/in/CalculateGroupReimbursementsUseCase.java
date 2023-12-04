package pl.kondziet.springbackend.application.port.in;

import pl.kondziet.springbackend.domain.model.entity.Reimbursement;
import pl.kondziet.springbackend.domain.model.valueobjects.GroupId;

import java.util.List;

public interface CalculateGroupReimbursementsUseCase {

    List<Reimbursement> calculateReimbursements(GroupId groupId);
}
