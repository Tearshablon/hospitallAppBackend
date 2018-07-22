package interfaces;

import domain.Doctor;
import lombok.NonNull;

import java.util.List;

public interface DoctorRepository {

    void saveDoctor(@NonNull Doctor doctor, Long departmentId);

    @NonNull
    List<Doctor> findAllDoctors();

    @NonNull
    Doctor findDoctor(@NonNull Long id);

    void deleteDoctor(@NonNull Long id);
}