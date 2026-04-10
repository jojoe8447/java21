package jo.seongju.emp;

import jo.seongju.emp.employee.CreateEmployeeRequest;
import jo.seongju.emp.employee.Department;
import jo.seongju.emp.employee.EmployeeService;
import jo.seongju.emp.employee.EmploymentStatus;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class EmployeeManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
    }

    @Bean
    ApplicationRunner seedEmployees(EmployeeService employeeService) {
        return args -> {
            if (!employeeService.isEmpty()) {
                return;
            }

            var seedData = List.of(
                    new CreateEmployeeRequest(
                            "Kim Minji",
                            "minji.kim@example.com",
                            Department.ENGINEERING,
                            EmploymentStatus.ACTIVE,
                            LocalDate.of(2024, 3, 4),
                            Set.of("java", "spring boot", "api")
                    ),
                    new CreateEmployeeRequest(
                            "Park Jihoon",
                            "jihoon.park@example.com",
                            Department.HR,
                            EmploymentStatus.PROBATION,
                            LocalDate.of(2025, 9, 1),
                            Set.of("recruiting", "people ops")
                    ),
                    new CreateEmployeeRequest(
                            "Lee Sujin",
                            "sujin.lee@example.com",
                            Department.SALES,
                            EmploymentStatus.ACTIVE,
                            LocalDate.of(2023, 11, 13),
                            Set.of("b2b", "crm", "presentation")
                    )
            );

            seedData.forEach(request -> employeeService.handle(request, null));
        };
    }
}
