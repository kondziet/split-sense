package pl.kondziet.springbackend.infrastructure.web;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kondziet.springbackend.application.service.AuthenticationPrincipalService;
import pl.kondziet.springbackend.application.service.CalculateGroupReimbursementService;
import pl.kondziet.springbackend.domain.model.valueobjects.Reimbursement;
import pl.kondziet.springbackend.infrastructure.security.userdetails.AppUserDetails;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/reimbursement")
public class ReimbursementController {

    private final AuthenticationPrincipalService authenticationPrincipalService;
    private final CalculateGroupReimbursementService calculateGroupReimbursementService;

    @GetMapping("/group/{groupId}")
    ResponseEntity<?> getGroupReimbursement(@PathVariable("groupId") UUID groupId) {
        AppUserDetails authenticatedUser = authenticationPrincipalService.getAuthenticatedUser();

        List<Reimbursement> reimbursements = calculateGroupReimbursementService.calculateReimbursements(groupId);

        return ResponseEntity.ok(reimbursements);
    }
}
