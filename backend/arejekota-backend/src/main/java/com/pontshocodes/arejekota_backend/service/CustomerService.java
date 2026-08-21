package com.pontshocodes.arejekota_backend.service;

import com.pontshocodes.arejekota_backend.entity.Customer;
import com.pontshocodes.arejekota_backend.exeption.EmailAlreadyExistsException;
import com.pontshocodes.arejekota_backend.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final JwtService jwtService;


    //Constructor Injection
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, OtpService otpService ,JwtService jwtService) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
        this.jwtService=jwtService;

    }

    public Customer registerCustomer(String firstName, String lastName, String email, String password) {
        // Validating values before using our method
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First Name is required");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last Name is required");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailPattern)) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is required.");
        }
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$";
        if (!password.matches(passwordPattern)) {
            throw new IllegalArgumentException("Password must be 8 characters.");
        }

        if (customerRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("An account with this email already exists.");
        }

        String hashedPassword = passwordEncoder.encode(password);
        Customer customer = new Customer(firstName, lastName, email, hashedPassword);
        Customer savedCustomer = customerRepository.save(customer);

        otpService.generateOTP(savedCustomer);

        return savedCustomer;
    }

    @Transactional
    public void activateAccount(String email, String submittedCode) {
        Optional<Customer> result = customerRepository.findByEmail(email);
        Customer customer;//declaring a variable that can refer to a customer object
        if (result.isPresent()) {
            customer = result.get(); //Assigning customer object in result to the customer variable
        } else {
            throw new IllegalArgumentException("No account exist for this email");
        }
        if (customer.isActive()) {
            throw new IllegalArgumentException("This account is already active");
        }
        boolean isVerified = otpService.verifyOtp(customer, submittedCode);
        if (isVerified) {
            customer.setActive(true);
            customerRepository.save(customer);
        }
    }


    public void resendOtp(String email) {
        Optional<Customer> result = customerRepository.findByEmail(email);
        Customer customer;
        if (result.isPresent()) {
            customer = result.get();
        } else {
            throw new IllegalArgumentException("An account with this  email is not found");
        }
        //Checking if the account is active
        if (customer.isActive()) {
            throw new IllegalArgumentException("This account is already active");
        }
        otpService.generateOTP(customer);

    }

    public void updateCustomer(String email, String newFirstName, String newLastName) {
        Optional<Customer> result = customerRepository.findByEmail(email);
        Customer customer;
        if (result.isPresent()) {
            customer = result.get();
        } else {
            throw new IllegalArgumentException("No account found for this email");
        }
        if (newFirstName == null || newFirstName.isBlank()) {
            throw new IllegalArgumentException("New first name can not be empty");
        }
        if (newLastName == null || newLastName.isBlank()) {
            throw new IllegalArgumentException("New last name can not be empty");
        }
        customer.setFirstName(newFirstName);
        customer.setLastName(newLastName);
        customerRepository.save(customer);
    }

    public void deleteAccount(String email, String submittedOtp) {
        if (submittedOtp == null || submittedOtp.isBlank()) {
            throw new IllegalArgumentException("OTP can not be null");
        }
        Optional<Customer> results = customerRepository.findByEmail(email);
        Customer customer;
        if (results.isPresent()) {
            customer = results.get();
        } else {
            throw new IllegalArgumentException("No account exists with this email");
        }
        if (!customer.isActive()) {
            throw new IllegalArgumentException("This account is not active");
        }

        boolean isVerified = otpService.verifyOtp(customer, submittedOtp);
        if (isVerified) {
            customerRepository.delete(customer);
        }
    }

    public void requestPasswordReset(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email can not be empty");
        }
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailPattern)) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }

        Optional<Customer> result = customerRepository.findByEmail(email);
        Customer customer;
        if (result.isPresent()) {
            customer = result.get();
        } else {
            throw new IllegalArgumentException("An account with this email does not exist");
        }
        if (!customer.isActive()) {
            throw new IllegalArgumentException("Please activate your account first");
        }
        otpService.generateOTP(customer);

    }

    @Transactional
    public void confirmPasswordReset(String email, String submittedOtp, String newPassword) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailPattern)) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }
        if (submittedOtp == null || submittedOtp.isBlank()) {
            throw new IllegalArgumentException("OTP can not be empty");
        }

        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("Password is required.");
        }

        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$";
        if (!newPassword.matches(passwordPattern)) {
            throw new IllegalArgumentException("Password must be 8 characters.");
        }
        Optional<Customer> result = customerRepository.findByEmail(email);
        Customer customer;
        if (result.isPresent()) {
            customer = result.get();
        } else {
            throw new IllegalArgumentException("No account found for this email");
        }
        if (!customer.isActive()) {
            throw new IllegalArgumentException("Please activate your account first");
        }

        boolean isVerified = otpService.verifyOtp(customer, submittedOtp);
        if (isVerified) {
            String newPasswordHash = passwordEncoder.encode(newPassword);
            customer.setPasswordHash(newPasswordHash);//replacing the old password
            customerRepository.save(customer);

        }

    }

    public String login(String email, String password) {

        Optional<Customer> result = customerRepository.findByEmail(email);
        Customer customer;

        if (result.isPresent()) {
            customer = result.get();
        } else {
            throw new IllegalArgumentException("No account found for this email");
        }

        if (!customer.isActive()) {
            throw new IllegalArgumentException("Please activate your account first");
        }

        if (!passwordEncoder.matches(password, customer.getPasswordHash())) {
            throw new IllegalArgumentException("Incorrect password");
        }

        return jwtService.generateToken(customer.getEmail(), "CUSTOMER");
    }


}



