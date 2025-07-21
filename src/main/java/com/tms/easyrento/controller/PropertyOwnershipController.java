package com.tms.easyrento.controller;

import com.tms.easyrento.dto.request.PropertyOwnershipRequest;
import com.tms.easyrento.globals.BaseController;
import com.tms.easyrento.globals.GlobalApiResponse;
import com.tms.easyrento.service.PropertyOwnershipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-11 20:08
 */

@RestController
@RequestMapping("/api/v1/property-ownership")
@RequiredArgsConstructor
public class PropertyOwnershipController extends BaseController {

    private final PropertyOwnershipService propertyOwnershipService;

    @PostMapping("/assign")
    public ResponseEntity<GlobalApiResponse> assignPropertyOwnership(
            @RequestBody @Valid PropertyOwnershipRequest propertyOwnershipRequest) {
        propertyOwnershipService.assignOwnership(propertyOwnershipRequest);
        return new ResponseEntity<>(successResponse("success",
                null),
                HttpStatus.OK);
    }

    @PostMapping("/assign-multiple")
    public ResponseEntity<GlobalApiResponse> assignPropertyOwnershipMultiple(
            @RequestBody @Valid PropertyOwnershipRequest propertyOwnershipRequest) {
        propertyOwnershipService.assignOwnership(propertyOwnershipRequest);
        return new ResponseEntity<>(
                successResponse("success", null), HttpStatus.OK);
    }
}
