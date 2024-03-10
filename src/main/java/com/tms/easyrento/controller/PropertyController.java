package com.tms.easyrento.controller;

import com.tms.easyrento.dto.request.PropertyRequest;
import com.tms.easyrento.globals.BaseController;
import com.tms.easyrento.globals.GlobalApiResponse;
import com.tms.easyrento.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/7/23 9:35 PM
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/properties")
public class PropertyController extends BaseController {

    private final PropertyService propertyService;

    private static final String PROPERTY_IMAGE_LOCATION = System.getProperty("user.home") + File.separator + "PROPERTY_IMAGE";

    @PostMapping
    ResponseEntity<GlobalApiResponse> createProduct( @ModelAttribute @Valid PropertyRequest propertyRequest) {

        return new ResponseEntity<>(successResponse("created",
                propertyService.create(propertyRequest)),
                HttpStatus.OK);
    }

    @PostMapping(value = "/file-upload")
    ResponseEntity<GlobalApiResponse> uploadFile(@RequestPart("files") MultipartFile file) {
        return  new ResponseEntity<>(successResponse("success",
                null),
                HttpStatus.OK);
    }

    @GetMapping("/get-info/{id}")
    ResponseEntity<GlobalApiResponse> getPropertyForPublic(@Valid @PathVariable("id") Long propertyId) {

        return new ResponseEntity<>(successResponse("success",
                propertyService.getSinglePropertyInfo(propertyId)),
                HttpStatus.OK);
    }

    @GetMapping("/owner")
    ResponseEntity<GlobalApiResponse> getPropertyForOwner() {

        return new ResponseEntity<>(successResponse("success",
                propertyService.getPropertyBy()),
                HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    ResponseEntity<GlobalApiResponse> getPropertyByOwnerId(@Valid @PathVariable("id") Long propertyId) {

        return new ResponseEntity<>(successResponse("success",
                propertyService.getPropertyInfo(propertyId)),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<GlobalApiResponse> updateProperty(@Valid @RequestBody PropertyRequest request,
                                                    @PathVariable("id") Long propertyId) {

        return new ResponseEntity<>(successResponse("updated",
                propertyService.update(request, propertyId)),
                HttpStatus.OK);
    }

    @GetMapping("/get-info/all")
    ResponseEntity<GlobalApiResponse> getProperties(@RequestParam(value = "isActive", defaultValue = "1") String isActive) {

        return new ResponseEntity<>(successResponse("success",
                propertyService.read(isActive)),
                HttpStatus.OK);
    }

    @GetMapping("/get")
    ResponseEntity<GlobalApiResponse> getAllProducts(@RequestParam(value = "isActive", defaultValue = "1") String isActive) {

        return new ResponseEntity<>(successResponse("success",
                propertyService.read(isActive)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<GlobalApiResponse> softDelete(@PathVariable("id") Long propertyId) {
        propertyService.delete(propertyId);
        return new ResponseEntity<>(successResponse("success",
                null),
                HttpStatus.OK);
    }

    @GetMapping("/get-info")
    ResponseEntity<GlobalApiResponse> getAllData() {

        return new ResponseEntity<>(successResponse("success",
                propertyService.getPropertyMetaInfo()),
                HttpStatus.OK);
    }

    @GetMapping("/get-image/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {
        byte[] image = new byte[0];
        try {
            image = Files.readAllBytes(new File(PROPERTY_IMAGE_LOCATION + File.separator + filename).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

}
