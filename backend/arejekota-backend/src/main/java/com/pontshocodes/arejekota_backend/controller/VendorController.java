package com.pontshocodes.arejekota_backend.controller;

import com.pontshocodes.arejekota_backend.dto.LoginRequest;
import com.pontshocodes.arejekota_backend.dto.VendorRegistrationRequest;
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
        this.vendorService = vendorService;
    }
    public ResponseEntity<String> register (@RequestBody VendorRegistrationRequest request){
        vendorService.register(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPhoneNumnber(),
                request.getBussinessName(),
                request.getAddress()
        );
        return ResponseEntity.ok("Vendor registration request successful");
    }


}
