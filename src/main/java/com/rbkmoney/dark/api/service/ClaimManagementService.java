package com.rbkmoney.dark.api.service;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.converter.claimmanagement.ClaimManagementConverter;
import com.rbkmoney.swag.claim_management.model.Claim;
import com.rbkmoney.swag.claim_management.model.ClaimChangeset;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClaimManagementService {

    private final ClaimManagementSrv.Iface claimManagementClient;

    private final ClaimManagementConverter claimManagementConverter;

    public Claim createClaim(String partyId, ClaimChangeset changeset) throws TException {
        List<Modification> modificationList = claimManagementConverter.convertChangesetToThrift(changeset);
        com.rbkmoney.damsel.claim_management.Claim claim = claimManagementClient.createClaim(partyId, modificationList);
        return claimManagementConverter.convertClaimToSwag(claim);
    }

    public Claim getClaimById(String partyId, Long claimId) throws TException {
        com.rbkmoney.damsel.claim_management.Claim claim = claimManagementClient.getClaim(partyId, claimId);
        return claimManagementConverter.convertClaimToSwag(claim);
    }

    public List<Claim> searchClaims(String partyId, Integer limit, String continuationToken, List<String> claimStatuses)
            throws TException {
        ClaimSearchQuery claimSearchQuery =
                claimManagementConverter.convertSearchClaimsToThrift(partyId, limit, continuationToken, claimStatuses);
        List<com.rbkmoney.damsel.claim_management.Claim> claimList = claimManagementClient.searchClaims(claimSearchQuery);
        return claimManagementConverter.convertClaimListToSwag(claimList);
    }

    public void updateClaimById(String partyId,
                                Long claimId,
                                Integer claimRevision,
                                List<com.rbkmoney.swag.claim_management.model.Modification> changeset) throws TException {
        List<Modification> modificationList = claimManagementConverter.convertModificationUnitToThrift(changeset);
        claimManagementClient.updateClaim(partyId, claimId, claimRevision, modificationList);
    }

    public void revokeClaimById(String partyId, Long claimId, Integer claimRevision, String reason) throws TException {
        claimManagementClient.revokeClaim(partyId, claimId, claimRevision, reason);
    }

}
