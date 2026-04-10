package jo.seongju.emp.employee;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeResponse> list(Optional<String> keyword) {
        var employees = employeeRepository.findAll().stream();

        var filteredEmployees = keyword
                .map(String::strip)
                .filter(Predicate.not(String::isBlank))
                .map(value -> employees.filter(employee -> matches(employee, value)))
                .orElse(employees);

        return filteredEmployees
                .map(EmployeeResponse::from)
                .toList();
    }

    public EmployeeResponse get(UUID id) {
        return employeeRepository.findById(id)
                .map(EmployeeResponse::from)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public EmployeeResponse handle(EmployeeCommand command, UUID employeeId) {
        return switch (command) {
            case CreateEmployeeRequest createRequest -> create(createRequest);
            case UpdateEmployeeRequest updateRequest -> update(employeeId, updateRequest);
        };
    }

    public void delete(UUID id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
    }

    public boolean isEmpty() {
        return employeeRepository.isEmpty();
    }

    private EmployeeResponse create(CreateEmployeeRequest request) {
        var employee = new Employee(
                null,
                request.name(),
                request.email(),
                request.department(),
                request.status(),
                request.hiredDate(),
                request.skills()
        );

        return EmployeeResponse.from(employeeRepository.save(employee));
    }

    private EmployeeResponse update(UUID id, UpdateEmployeeRequest request) {
        var currentEmployee = employeeRepository.findById(requireId(id))
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        var updatedEmployee = new Employee(
                currentEmployee.id(),
                request.name(),
                request.email(),
                request.department(),
                request.status(),
                request.hiredDate(),
                request.skills()
        );

        return EmployeeResponse.from(employeeRepository.save(updatedEmployee));
    }

    private boolean matches(Employee employee, String keyword) {
        var normalizedKeyword = keyword.toLowerCase();

        return employee.name().toLowerCase().contains(normalizedKeyword)
                || employee.email().toLowerCase().contains(normalizedKeyword)
                || employee.department().displayName().toLowerCase().contains(normalizedKeyword)
                || employee.status().displayName().toLowerCase().contains(normalizedKeyword)
                || employee.skills().stream().anyMatch(skill -> skill.contains(normalizedKeyword));
    }

    private UUID requireId(UUID employeeId) {
        return Optional.ofNullable(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("employeeId is required for updates"));
    }
}
