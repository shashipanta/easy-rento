package com.tms.easyrento.controller;

import com.tms.easyrento.dto.request.LoginRequest;
import com.tms.easyrento.dto.request.UserRequest;
import com.tms.easyrento.globals.BaseController;
import com.tms.easyrento.globals.GlobalApiResponse;
import com.tms.easyrento.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/5/23 11:55 PM
 */

@CrossOrigin( origins = "http://127.0.0.1:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user-accounts")
public class AuthController extends BaseController {

    public final UserAccountService userAccountService;

    @PostMapping("/auth/register")
    public ResponseEntity<GlobalApiResponse> registerNewAccount(@Valid @RequestBody UserRequest request) {
        return new ResponseEntity<>(successResponse("created",
                userAccountService.create(request)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalApiResponse> softDeleteAccounts(@PathVariable(value = "id") Long userId,
                                                                @RequestParam(value = "isActive", defaultValue = "1") Long isActive) {
        userAccountService.softDelete(userId, isActive);
        return new ResponseEntity<>(successResponse("deleted",
                null),
                HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<GlobalApiResponse> getAllUserAccounts(@RequestParam(value = "isActive", defaultValue = "1") Long isActive) {
        return new ResponseEntity<>(successResponse("success",
                userAccountService.listAll(isActive)),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalApiResponse> getSingleUserAccount(@PathVariable(value = "id") Long userId) {
        return new ResponseEntity<>(successResponse("success",
                userAccountService.getById(userId)),
                HttpStatus.OK);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<GlobalApiResponse> handleAuthentication(@RequestBody LoginRequest loginRequest, Authentication authentication) {
        // Your authentication logic here
        return new ResponseEntity<>(successResponse("success",
                userAccountService.generateToken(loginRequest)),
        HttpStatus.OK);
    }


}
