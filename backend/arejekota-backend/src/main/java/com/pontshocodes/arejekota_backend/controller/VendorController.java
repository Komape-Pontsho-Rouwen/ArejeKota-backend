package com.pontshocodes.arejekota_backend.controller;

import com.pontshocodes.arejekota_backend.dto.LoginRequest;
import com.pontshocodes.arejekota_backend.service.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService){
        this.vendorService=vendorService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody LoginRequest request){
        String token = vendorService.login(
                request.getEmail(),
                request.getPassword()
        );
        return ResponseEntity.ok(token);

    }
}
