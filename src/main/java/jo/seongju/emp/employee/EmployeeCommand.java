package jo.seongju.emp.employee;

public sealed interface EmployeeCommand permits CreateEmployeeRequest, UpdateEmployeeRequest {
}
