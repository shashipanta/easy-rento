package com.tms.easyrento.admin;

import java.util.List;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-06-28 10:39
 */
public interface PolicyService {
    List<Policy> getAllPolicies();
    Policy getPolicyById(Long id);
    Policy savePolicy(Policy policy);
    void deletePolicy(Long id);
    List<Policy> getPoliciesForRole(String roleName);
}

