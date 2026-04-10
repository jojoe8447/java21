package jo.seongju.emp.employee;

public enum EmploymentStatus {
    PROBATION,
    ACTIVE,
    INACTIVE;

    public String displayName() {
        return switch (this) {
            case PROBATION -> "Probation";
            case ACTIVE -> "Active";
            case INACTIVE -> "Inactive";
        };
    }
}
