package com.tms.easyrento.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-06-28 19:48
 */

@ControllerAdvice
public class GlobalMvcExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("toastType", "error");
        redirectAttributes.addFlashAttribute("toastMessage", "Database constraint violation: " + ex.getMostSpecificCause().getMessage());

        // Dynamically determine where to redirect back to (fallback if needed)
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/admin/policies");
    }
}

