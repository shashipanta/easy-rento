//package com.tms.easyrento.config.security.authority;
//
//import com.tms.easyrento.admin.PolicyService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.lang.NonNull;
//import org.springframework.lang.NonNullApi;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import java.util.Map;
//
///**
// * @author barbosa
// * @version 1.0.0
// * @since 2025-06-28 10:29
// */
//
//@Component
//@RequiredArgsConstructor
//public class PolicyInterceptor implements HandlerInterceptor {
//
//    private final PolicyService policyService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request,
//                             @NonNull HttpServletResponse response,
//                             @NonNull Object handler) throws Exception {
//
//        String username = request.getUserPrincipal().getName();
//        String requestURI = request.getRequestURI();
//        String method = request.getMethod();
//        String ip = request.getRemoteAddr();
//
//        var attributes = Map.of(
//                "ip", ip,
//                "method", method,
//                "uri", requestURI
//        );
//
//        // calling internal policy service
//        boolean hasAuthority = policyService.isAllowed(username, requestURI, method, attributes);
//
//        if (!hasAuthority) {
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission to access this resource");
//            return false;
//        }
//        return true;
//    }
//}
