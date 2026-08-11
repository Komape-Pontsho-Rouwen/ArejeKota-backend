package com.pontshocodes.arejekota_backend.service;

import com.pontshocodes.arejekota_backend.entity.Customer;
import com.pontshocodes.arejekota_backend.exeption.EmailAlreadyExistsException;
import com.pontshocodes.arejekota_backend.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;


    //Constructor Injection
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, OtpService otpService) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;

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

        return savedCustomer;
    }

    public void activateAccount(String email, String submittedCode) {
        boolean isVerified = otpService.verifyOtp(email, submittedCode);
        //Runs for only for the correct otp and then search for the email
        if (isVerified) {
            Optional<Customer> result = customerRepository.findByEmail(email);//Find the customer with this email
            Customer customer;//declaring a variable that can refer to a customer object
            if (result.isPresent()) {
                customer = result.get(); //Assigning customer object in result to the customer variable
            } else {
                throw new IllegalArgumentException("No account exist for this email");
            }
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
        if (!customer.isActive()) {
            throw new IllegalArgumentException("This account is already active");
        }
        otpService.generateOTP(email);

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
        boolean isVerified = otpService.verifyOtp(email, submittedOtp);
        if (isVerified) {
            Optional<Customer> result = customerRepository.findByEmail(email);
            Customer customer;
            if (result.isPresent()) {
                customer = result.get();
            } else {
                throw new IllegalArgumentException("No account found for this email");
            }
            customerRepository.delete(customer);
        }
    }

    public void resetPassword(String email) {
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
        otpService.generateOTP(email);

    }

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

        boolean isVerified = otpService.verifyOtp(email, submittedOtp);
        if (isVerified) {
            Optional<Customer> result = customerRepository.findByEmail(email); //Searching an account with this email
            Customer customer;
            if (result.isPresent()) {
                customer = result.get();
            } else {
                throw new IllegalArgumentException("There is no account with this email");
            }


            String newPasswordHash = passwordEncoder.encode(newPassword);
            customer.setPasswordHash(newPasswordHash);//replacing the old password
            customerRepository.save(customer);

        }

    }

}



