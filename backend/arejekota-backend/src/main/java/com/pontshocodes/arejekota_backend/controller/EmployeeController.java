package com.pontshocodes.arejekota_backend.controller;

import com.pontshocodes.arejekota_backend.dto.EmployeeRegistrationRequest;
import com.pontshocodes.arejekota_backend.entity.Vendor;
import com.pontshocodes.arejekota_backend.repository.VendorRepository;
import com.pontshocodes.arejekota_backend.service.EmployeeRegistrationResult;
import com.pontshocodes.arejekota_backend.service.EmployeeService;
import com.pontshocodes.arejekota_backend.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor/employees")
public class EmployeeController{
    private final EmployeeService employeeService;
    private final VendorRepository vendorRepository;
    private final JwtService jwtService;

    public EmployeeController(EmployeeService employeeService ,VendorRepository vendorRepository ,JwtService jwtService){
        this.employeeService=employeeService;
        this.vendorRepository=vendorRepository;
        this.jwtService=jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<EmployeeRegistrationResult> register (@RequestBody EmployeeRegistrationRequest request ,@RequestHeader("Authorization")
                                                                String authHeader){
        String token = authHeader.replace("Bearer " ,"");
        String vendorEmail = jwtService.extractEmail((token));

        Vendor vendor = vendorRepository.findByEmail(vendorEmail).orElseThrow(() -> new IllegalArgumentException("Vendor not found for this token"));
        EmployeeRegistrationResult result = employeeService.registerEmployee(
                request.getFirstName(),
                request.getLastName(),
                request.getRole(),
                vendor
        );
        return ResponseEntity.ok(result);
    }
}

