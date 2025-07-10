package com.tms.easyrento.service.impl;

import com.tms.easyrento.config.security.service.JwtService;
import com.tms.easyrento.dto.request.OwnerRequest;
import com.tms.easyrento.dto.response.OwnerResponse;
import com.tms.easyrento.dto.response.RentalOfferResponse;
import com.tms.easyrento.dto.response.TenantResponse;
import com.tms.easyrento.mappers.OwnerMapper;
import com.tms.easyrento.model.auth.UserAccount;
import com.tms.easyrento.model.owner.Owner;
import com.tms.easyrento.repository.ContractRepo;
import com.tms.easyrento.repository.OwnerRepo;
import com.tms.easyrento.repository.UserAccountRepository;
import com.tms.easyrento.service.ContractService;
import com.tms.easyrento.service.OwnerService;
import com.tms.easyrento.service.RentalOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/8/23 6:04 AM
 */
@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final ContractService contractService;
    private final RentalOfferService rentalOfferService;

    private final UserAccountRepository userAccountRepo;
    private final ContractRepo contractRepo;
    private final OwnerRepo ownerRepo;

    private final OwnerMapper ownerMapper;

    private final JwtService jwtService;

    @Override
    public OwnerResponse read(Long aLong) {
        Owner owner = ownerRepo.findById(aLong).orElseThrow();
        return ownerMapper.entityToResponse(owner);
    }

    @Override
    public Long create(OwnerRequest request) {
        Owner owner = ownerMapper.requestToEntity(request);
        return ownerRepo.save(owner).getId();
    }

    @Override
    public Long update(OwnerRequest request, Long aLong) {
        return null;
    }

    @Override
    public List<OwnerResponse> read(String isActive) {
        List<Owner> owners = ownerRepo.getAll(isActive);
        return owners.stream()
                .map(ownerMapper::entityToResponse)
                .toList();
    }

    @Override
    public void delete(Long aLong) {
        ownerRepo.toggleActiveStatus(aLong);
    }

    @Override
    public boolean hardDelete(Long aLong) {
        return false;
    }

    @Override
    public Owner model(Long aLong) {
        return ownerRepo.findById(aLong).orElseThrow();
    }

    @Override
    public Boolean terminateContract(Long contractId) {
        contractRepo.terminateContractById(contractId);
        return Boolean.TRUE;
    }
    @Override
    public List<TenantResponse> associatedTenants() {
        // get ownerId from jwt token
        return contractService.getAssociatedTenants(1l);
    }

    @Override
    public List<RentalOfferResponse> rentalOffers() {
        Long ownerId = Long.valueOf(jwtService.extractClaimForLoggedInUser("userId"));
        return rentalOfferService.getOffers(ownerId);
    }

    @Override
    public Integer rentOfferCounts() {
        Long ownerId = jwtService.getLoggedUserId();
        return Math.toIntExact(rentalOfferService.totalOffersBy(ownerId));
    }

    /**
     * Fetch the owner if it's already present else return the default owner with user_account attached
     *
     * @param id : Id of {@link Owner} that is used to fetch the owner
     * @return
     */
    @Override
    public Owner findByUserAccountIdOrGet(Long id) {
        Optional<Owner> ownerOpt = ownerRepo.findByUserAccount_Id(id);
        if (ownerOpt.isPresent()) {
            return ownerOpt.get();
        }

        UserAccount userAccount = userAccountRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User Id not found"));

        return new Owner(userAccount.getId(),  userAccount.getUsername(), "user_name_np");
    }

}
