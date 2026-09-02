package com.pontshocodes.arejekota_backend.dto;

import com.pontshocodes.arejekota_backend.entity.EmployeeRole;

public class EmployeeRegistrationRequest {
    private String firstName;
    private String lastName;
    private EmployeeRole role;

    public void setFirstName(String firstName){ this.firstName=firstName;}
    public String getFirstName(){return firstName;}
    public void setLastName(String lastName){this.lastName=lastName;}
    public String getLastName(){return lastName;}
    public void setRole(EmployeeRole role){this.role=role;}
    public EmployeeRole getRole(){return role;}

}
