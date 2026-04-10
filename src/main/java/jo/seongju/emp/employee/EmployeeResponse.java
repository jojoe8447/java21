package jo.seongju.emp.employee;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record EmployeeResponse(
        UUID id,
        String name,
        String email,
        Department department,
        String departmentLabel,
        EmploymentStatus status,
        String statusLabel,
        LocalDate hiredDate,
        List<String> skills,
        String summary
) {
    public static EmployeeResponse from(Employee employee) {
        var summary = """
                %s joined on %s and is currently %s in %s.
                """.formatted(
                employee.name(),
                employee.hiredDate(),
                employee.status().displayName(),
                employee.department().displayName()
        ).trim();

        return new EmployeeResponse(
                employee.id(),
                employee.name(),
                employee.email(),
                employee.department(),
                employee.department().displayName(),
                employee.status(),
                employee.status().displayName(),
                employee.hiredDate(),
                employee.skills().stream().sorted().toList(),
                summary
        );
    }
}
