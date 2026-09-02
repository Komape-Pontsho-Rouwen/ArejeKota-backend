package com.pontshocodes.arejekota_backend.service;


import com.pontshocodes.arejekota_backend.entity.Employee;
import com.pontshocodes.arejekota_backend.entity.EmployeeRole;
import com.pontshocodes.arejekota_backend.entity.Vendor;
import com.pontshocodes.arejekota_backend.repository.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String EMAIL_DOMAIN= "arejekota.co.za";
    private static final String PASSWORD_CHARS ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz23456789";
    private static final SecureRandom RANDOM= new SecureRandom();


    public EmployeeService(EmployeeRepository employeeRepository ,PasswordEncoder passwordEncoder){
        this.employeeRepository=employeeRepository;
        this.passwordEncoder=passwordEncoder;
    }
    public EmployeeRegistrationResult registerEmployee(String firstName , String lastName , EmployeeRole role , Vendor vendor){
        String workEmail = generateWorkEmail(firstName);
        String employeeId = generateEmployeeId(role);
        String tempPassword = generateTemporaryPassword();

        Employee employee = new Employee(employeeId , firstName,lastName,workEmail,passwordEncoder.encode(tempPassword),role,vendor);

        employeeRepository.save(employee);

        return new EmployeeRegistrationResult(workEmail,tempPassword ,employeeId);

    }

    private String generateWorkEmail(String firstName){
        String base = firstName.trim().toLowerCase();
        int suffix = 1;
        String candidate;
        do{
            candidate = base + String.format("%02d" ,suffix) + EMAIL_DOMAIN;
            suffix++;
        }while(employeeRepository.existsByEmail(candidate));

        return candidate;
    }

    public String generateEmployeeId(EmployeeRole role){
        long count = employeeRepository.countByRole(role) +1;
        String prefix = (role == EmployeeRole.DRIVER)? "DRV" : "CASH";
        return prefix + "-" + String.format("%05d" ,count);
    }
    public String generateTemporaryPassword(){
        StringBuilder passwordBuilder = new StringBuilder();
        for(int i=0 ; i<10 ; i++){
            passwordBuilder.append(PASSWORD_CHARS.charAt(RANDOM.nextInt(PASSWORD_CHARS.length())));
        };
        return passwordBuilder.toString();
    }
    public void login(String workEmail ,String temporaryPassword,String employeeId){
        if(workEmail ==  null || workEmail.isEmpty()){
            throw new IllegalArgumentException("Work email can not be null");
        }
        if(temporaryPassword==null || temporaryPassword.isEmpty()){
            throw new IllegalArgumentException("Password can not be empty");
        }
        if(employeeId ==null || employeeId.isEmpty()){
            throw new IllegalArgumentException(("Employee id can not be empty"));
        }
        Optional<Employee> results = employeeRepository.findByEmail(workEmail);
        Employee employee;
        if(results.isPresent()){
            employee = results.get();
        }
        else{
            throw new IllegalArgumentException("No account found for this email");
        }
        if(!temporaryPassword.matches(employee.getPasswordHash())) {
            throw new IllegalArgumentException("Incorrect Password");
        }
        }

}
