package interfaces;

import lombok.*;
import domain.Department;

import java.util.List;

public interface DepartmentRepository {

    void saveDepartment(@NonNull Department department);

    @NonNull
    List<Department> findAllDepartmentsWithDoctors();

    @NonNull
    Department findDepartmentWithoutDoctors(@NonNull Long id);

    @NonNull
    List<Department> findAllDepartmentWithoutDoctors();

    void deleteDepartment(@NonNull Long id);
}

