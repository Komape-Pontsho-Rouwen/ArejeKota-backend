package com.pontshocodes.arejekota_backend.controller;

import com.pontshocodes.arejekota_backend.dto.LoginRequest;
import com.pontshocodes.arejekota_backend.dto.PendingVendorResponse;
import com.pontshocodes.arejekota_backend.dto.VendorApprovalResponse;
import com.pontshocodes.arejekota_backend.entity.Vendor;
import com.pontshocodes.arejekota_backend.service.AdminService;
import com.pontshocodes.arejekota_backend.service.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final AdminService adminService;
    private final VendorService vendorService;

    public AdminController(AdminService adminService , VendorService vendorService){
        this.adminService=adminService;
        this.vendorService=vendorService;

    }
    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody LoginRequest request) {
        adminService.login(
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok("Login Successful");
    }

    // Put - Approving the existing vendor account that are pending
    @PutMapping("/vendors/{id}/approve")
    public ResponseEntity<VendorApprovalResponse> approveVendor(@PathVariable Long id) {
        VendorApprovalResponse response = vendorService.approveVendor(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/vendors/{id}/reject")
    public ResponseEntity<String> rejectVendor(@PathVariable Long id){
        vendorService.rejectVendor(id);
        return ResponseEntity.ok("Vendor rejected");
    }
    // Get - Retrieving pending vendor account

    }






