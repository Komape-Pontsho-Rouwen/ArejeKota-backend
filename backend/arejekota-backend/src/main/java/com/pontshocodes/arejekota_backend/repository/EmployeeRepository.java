package com.pontshocodes.arejekota_backend.repository;

import com.pontshocodes.arejekota_backend.entity.Employee;
import com.pontshocodes.arejekota_backend.entity.EmployeeRole;
import com.pontshocodes.arejekota_backend.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmployeeId(String employeeId);

    List<Employee> findByVendor(Vendor vendor);
    long countByRole(EmployeeRole role);

}
