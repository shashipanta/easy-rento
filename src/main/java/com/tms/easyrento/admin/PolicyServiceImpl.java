package com.tms.easyrento.admin;

import com.tms.easyrento.model.auth.Role;
import com.tms.easyrento.repository.RoleRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-06-28 10:39
 */

@Service
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepo policyRepository;
    private final RoleRepo roleRepository;

    public PolicyServiceImpl(PolicyRepo policyRepository, RoleRepo roleRepository) {
        this.policyRepository = policyRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    @Override
    public Policy getPolicyById(Long id) {
        return policyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Policy not found"));
    }

    @Override
    public Policy savePolicy(Policy policy) {
        // Fetch attached roles from DB to ensure persistence context correctness
        Set<Role> attachedRoles = policy.getRoles().stream()
                .map(role -> roleRepository.findById(Math.toIntExact(role.getId()))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid role ID")))
                .collect(Collectors.toSet());
        policy.setRoles(attachedRoles);
        return policyRepository.save(policy);
    }

    @Override
    public void deletePolicy(Long id) {
        policyRepository.deleteById(id);
    }

    @Override
    public List<Policy> getPoliciesForRole(String roleName) {
        return policyRepository.findAll().stream()
                .filter(p -> p.getRoles().stream().anyMatch(r -> r.getName().equals(roleName)))
                .collect(Collectors.toList());
    }
}

