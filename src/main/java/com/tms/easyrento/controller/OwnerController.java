package com.tms.easyrento.controller;

import com.tms.easyrento.dto.request.OwnerRequest;
import com.tms.easyrento.globals.BaseController;
import com.tms.easyrento.globals.GlobalApiResponse;
import com.tms.easyrento.service.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/8/23 5:28 PM
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/owners")
public class OwnerController extends BaseController {

    private final OwnerService ownerService;


    @PostMapping()
    public ResponseEntity<GlobalApiResponse> createOwner(@Valid @RequestBody OwnerRequest request) {

        return new ResponseEntity<>(successResponse("created",
                ownerService.create(request)),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalApiResponse> view(@PathVariable("id") Long ownerId) {

        return new ResponseEntity<>(successResponse("success",
                ownerService.read(ownerId)),
                HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<GlobalApiResponse> viewAll(@RequestParam(name = "isActive",defaultValue = "1") String isActive) {

        return new ResponseEntity<>(successResponse("success",
                ownerService.read(isActive)),
                HttpStatus.OK);
    }


}
