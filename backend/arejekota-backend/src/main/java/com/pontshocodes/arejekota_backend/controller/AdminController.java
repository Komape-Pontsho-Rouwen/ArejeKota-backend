package com.pontshocodes.arejekota_backend.controller;

import com.pontshocodes.arejekota_backend.dto.LoginRequest;
import com.pontshocodes.arejekota_backend.entity.Vendor;
import com.pontshocodes.arejekota_backend.service.AdminService;
import com.pontshocodes.arejekota_backend.service.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final  AdminService adminService;
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
    @PostMapping("vendors/{id}/approve")
    public  ResponseEntity<String> approveVendor (@PathVariable Long id){
        vendorService.approveVendor(id);
        return ResponseEntity.ok("Vendor Approved Successfully");
    }
    @PostMapping("/vendors/{id}/reject")
    public ResponseEntity<String> rejectReject(@PathVariable Long id){
        vendorService.rejectVendor(id);
        return ResponseEntity.ok("Vendor rejected");
    }

    @GetMapping("/vendors/pending")
    public ResponseEntity<List<Vendor>> listPendingVendors(){
        List<Vendor> pendingVendors = vendorService.listPendingVendors();
        return ResponseEntity.ok(pendingVendors);
    }



}
