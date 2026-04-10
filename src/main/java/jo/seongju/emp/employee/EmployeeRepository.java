package jo.seongju.emp.employee;

import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class EmployeeRepository {

    private final ConcurrentMap<UUID, Employee> employees = new ConcurrentHashMap<>();

    public List<Employee> findAll() {
        return employees.values().stream()
                .sorted(Comparator.comparing(Employee::hiredDate).reversed().thenComparing(Employee::name))
                .toList();
    }

    public Optional<Employee> findById(UUID id) {
        return Optional.ofNullable(employees.get(id));
    }

    public Employee save(Employee employee) {
        employees.put(employee.id(), employee);
        return employee;
    }

    public void deleteById(UUID id) {
        employees.remove(id);
    }

    public boolean existsById(UUID id) {
        return employees.containsKey(id);
    }

    public boolean isEmpty() {
        return employees.isEmpty();
    }
}
