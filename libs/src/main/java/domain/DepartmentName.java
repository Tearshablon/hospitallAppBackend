package domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum DepartmentName {
    THERAPY("Терапия"),
    STOMATOLOGY("Cтоматология"),
    SURGERY("Хирургия"),
    NEUROLOGY("Неврология"),
    OPHTHALMOLOGY("Офтальмология");

    private final String departmentName;

    DepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public static DepartmentName fromString(String text) {
        for (DepartmentName b : DepartmentName.values()) {
            if (b.departmentName.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}