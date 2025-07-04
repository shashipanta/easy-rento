package com.tms.easyrento.admin;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-04 09:57
 */

@Controller
@RequestMapping("/admin")
public class AdminAuthController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "auth/admin-login";
    }

    @GetMapping("/dashboard")
    public String getAdminDashboard(Authentication auth, Model model) {
        model.addAttribute("authentication", auth);
        model.addAttribute("totalUsers", 1);
        model.addAttribute("activeProperties", 3);
        model.addAttribute("pendingRequests", 3);
        return "admin/dashboard";
    }
}
