package jo.seongju.emp.employee;

public enum Department {
    ENGINEERING,
    HR,
    SALES,
    OPERATIONS;

    public String displayName() {
        return switch (this) {
            case ENGINEERING -> "Engineering";
            case HR -> "Human Resources";
            case SALES -> "Sales";
            case OPERATIONS -> "Operations";
        };
    }
}
