package com.pontshocodes.arejekota_backend.dto;

public class VendorApprovalResponse {

    private Long vendorId;
    private String workEmail;
    private String temporaryPassword;

    public VendorApprovalResponse(Long vendorId, String workEmail, String temporaryPassword) {
        this.vendorId = vendorId;
        this.workEmail = workEmail;
        this.temporaryPassword = temporaryPassword;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public String getTemporaryPassword() {
        return temporaryPassword;
    }
}
