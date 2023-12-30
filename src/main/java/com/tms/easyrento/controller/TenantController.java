package com.tms.easyrento.controller;

import com.tms.easyrento.dto.request.TenantRequest;
import com.tms.easyrento.globals.BaseController;
import com.tms.easyrento.globals.GlobalApiResponse;
import com.tms.easyrento.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/12/23 12:28 AM
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tenants")
public class TenantController extends BaseController {

    private final TenantService tenantService;

    @PostMapping
    public ResponseEntity<GlobalApiResponse> createTenant(@Valid @RequestBody TenantRequest request) {

        return new ResponseEntity<>(successResponse("created",
                tenantService.create(request)),
                HttpStatus.CREATED);

    }


    @GetMapping("/{id}")
    public ResponseEntity<GlobalApiResponse> getTenant(@PathVariable("id") Long tenantId) {

        return new ResponseEntity<>(successResponse("success",
                tenantService.read(tenantId)),
                HttpStatus.OK);
    }



    @GetMapping()
    public ResponseEntity<GlobalApiResponse> getActiveTenants(
            @RequestParam(required = false, defaultValue = "1") String isActive) {


        return new ResponseEntity<>(successResponse("success",
                tenantService.read(isActive)),
                HttpStatus.OK);
    }

    @GetMapping("/owner-wise")
    public ResponseEntity<GlobalApiResponse> getTenantForOwner(
            @RequestParam(required = false, defaultValue = "1") String isActive) {

        return new ResponseEntity<>(successResponse("success",
                tenantService.read(isActive)),
                HttpStatus.OK);
    }
}
