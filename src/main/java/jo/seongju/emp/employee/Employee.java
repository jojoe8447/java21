package jo.seongju.emp.employee;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record Employee(
        UUID id,
        String name,
        String email,
        Department department,
        EmploymentStatus status,
        LocalDate hiredDate,
        Set<String> skills
) {
    public Employee {
        id = Objects.requireNonNullElseGet(id, UUID::randomUUID);
        name = requireText(name, "name");
        email = requireText(email, "email").toLowerCase(Locale.ROOT);
        department = Objects.requireNonNull(department, "department must not be null");
        status = Objects.requireNonNullElse(status, EmploymentStatus.ACTIVE);
        hiredDate = Objects.requireNonNullElseGet(hiredDate, LocalDate::now);

        var normalizedSkills = Optional.ofNullable(skills)
                .orElseGet(Set::of)
                .stream()
                .map(String::strip)
                .filter(Predicate.not(String::isBlank))
                .map(skill -> skill.toLowerCase(Locale.ROOT))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        skills = Set.copyOf(normalizedSkills);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value.strip();
    }
}
