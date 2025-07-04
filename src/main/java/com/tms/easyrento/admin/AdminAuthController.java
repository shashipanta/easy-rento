package com.tms.easyrento.admin;

import org.springframework.stereotype.Controller;
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
}
