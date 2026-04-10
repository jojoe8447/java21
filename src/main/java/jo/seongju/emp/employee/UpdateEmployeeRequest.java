package jo.seongju.emp.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record UpdateEmployeeRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotNull Department department,
        EmploymentStatus status,
        LocalDate hiredDate,
        Set<String> skills
) implements EmployeeCommand {
}
