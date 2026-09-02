package com.pontshocodes.arejekota_backend.service;

public class EmployeeRegistrationResult {
    private final String workEmail;
    private final String temporaryPassword;
    private final String employeeId;


    public EmployeeRegistrationResult(String workEmail,String temporaryPassword,String employeeId){
        this.workEmail=workEmail;
        this.temporaryPassword=temporaryPassword;
        this.employeeId=employeeId;
    }

    //Getters for our values
    public String getWorkEmail(){return workEmail;}
    public String getTemporaryPassword(){return temporaryPassword;}
    public String getEmployeeId(){return employeeId;}
}
