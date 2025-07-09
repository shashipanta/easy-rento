package com.tms.easyrento.admin;


import com.tms.easyrento.admin.dto.PolicyDto;
import com.tms.easyrento.repository.PermissionRepo;
import com.tms.easyrento.repository.RoleRepo;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-06-28 12:05
 */

@Controller
@RequestMapping("/admin/policies")
public class PolicyController {

    private final PolicyRepo policyRepo;
    private final RoleRepo roleRepo;
    private final EndpointScanner endpointScanner;
    private final PermissionRepo permissionRepo;

    public PolicyController(PolicyRepo policyRepo, RoleRepo roleRepo,
                            PermissionRepo permissionRepo,
                            EndpointScanner endpointScanner) {
        this.policyRepo = policyRepo;
        this.roleRepo = roleRepo;
        this.endpointScanner = endpointScanner;
        this.permissionRepo = permissionRepo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("policies", policyRepo.findAll());
        return "policy/policy-list";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("policy", new Policy());
        model.addAttribute("roles", roleRepo.findAll());
        model.addAttribute("permissions", permissionRepo.findAll());
        model.addAttribute("endpointsByMethod", endpointScanner.getGroupedEndpoints());
        return "policy/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Policy policy = policyRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid policy ID"));
        model.addAttribute("policy", policy);
        model.addAttribute("roles", roleRepo.findAll());
        model.addAttribute("permissions", permissionRepo.findAll());
        model.addAttribute("endpointsByMethod", endpointScanner.getGroupedEndpoints());
        return "policy/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute @Valid PolicyDto policyDto) {
        policyRepo.save(policyDto.toModel(policyDto));
        return "redirect:/admin/policies";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        policyRepo.deleteById(id);
        return "redirect:/admin/policies";
    }
}

