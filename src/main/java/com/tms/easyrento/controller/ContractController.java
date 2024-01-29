package com.tms.easyrento.controller;

import com.tms.easyrento.dto.request.ContractRequest;
import com.tms.easyrento.globals.BaseController;
import com.tms.easyrento.globals.GlobalApiResponse;
import com.tms.easyrento.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author shashi
 * @version 1.0.0
 * @since 1/27/24 10:24 PM
 */

@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
public class ContractController extends BaseController {

    private final ContractService contractService;

    @PostMapping
    public ResponseEntity<GlobalApiResponse> create(@RequestBody @Valid ContractRequest contractRequest) {

        return new ResponseEntity<>(successResponse("success",
                contractService.create(contractRequest)),
                HttpStatus.OK);
    }
}
