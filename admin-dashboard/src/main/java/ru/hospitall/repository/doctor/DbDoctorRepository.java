package ru.hospitall.repository.doctor;

import domain.Doctor;
import domain.HospitallAppException;
import interfaces.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.generate.tables.records.DoctorRecord;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.generate.tables.Doctor.DOCTOR;

@Slf4j
public class DbDoctorRepository implements DoctorRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public void saveDoctor(Doctor doctor, Long departmentId) {

        if (doctor.getId() == null) {
            insertDoctor(doctor, departmentId);
        } else {
            updateDoctor(doctor, departmentId);
        }
    }

    private void insertDoctor(Doctor doctor, Long departmentId) {
        try (Connection connection = dataSource.getConnection();
             DSLContext context = DSL.using(connection)) {

            int result = context.insertInto(DOCTOR)
                    .set(DOCTOR.FIRST_NAME, doctor.getFirstName())
                    .set(DOCTOR.LAST_NAME, doctor.getLastName())
                    .set(DOCTOR.DEPARTMENT_ID, departmentId == null ? null : departmentId.intValue())
                    .set(DOCTOR.CREATE_DATE, Timestamp.valueOf(LocalDateTime.now()))
                    .execute();

            if (result == 0) {
                throw new HospitallAppException("Cannot insert doctor:");
            }

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new HospitallAppException("Cannot insert doctor: ", e);
        }
    }

    private void updateDoctor(Doctor doctor, Long departmentId) {
        try (Connection connection = dataSource.getConnection();
             DSLContext context = DSL.using(connection)) {
            int result = context.update(DOCTOR)
                    .set(DOCTOR.FIRST_NAME, doctor.getFirstName())
                    .set(DOCTOR.LAST_NAME, doctor.getLastName())
                    .set(DOCTOR.DEPARTMENT_ID, departmentId == null ? null : departmentId.intValue())
                    .set(DOCTOR.MODIFY_DATE, Timestamp.valueOf(LocalDateTime.now()))
                    .where(DOCTOR.ID.eq(doctor.getId().intValue()))
                    .execute();

            if (result == 0) {
                throw new HospitallAppException("Cannot update doctor:");
            }

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new HospitallAppException("Cannot update doctor: ", e);
        }
    }

    @Override
    public List<Doctor> findAllDoctors() {
        try (Connection connection = dataSource.getConnection();
             DSLContext context = DSL.using(connection)) {

            return context.selectFrom(DOCTOR)
                    .where(DOCTOR.DELETED.ne((byte) 1))
                    .stream()
                    .map(doctorRecord ->
                            new Doctor(doctorRecord.getId().longValue(),
                                    doctorRecord.getFirstName(),
                                    doctorRecord.getLastName(),
                                    doctorRecord.getPhoto()))
                    .collect(Collectors.toList());

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new HospitallAppException("Cannot find doctors doctor: ", e);
        }
    }

    @Override
    public @NotNull Doctor findDoctor(Long id) {
        try (Connection connection = dataSource.getConnection();
             DSLContext context = DSL.using(connection)) {

            DoctorRecord doctorRecord = context
                    .selectFrom(DOCTOR)
                    .where(DOCTOR.ID.eq(id.intValue()))
                    .fetchOne();

            return new Doctor(doctorRecord.getId().longValue(),
                    doctorRecord.getFirstName(),
                    doctorRecord.getLastName(),
                    doctorRecord.getPhoto());

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new HospitallAppException("Cannot find doctors doctor: ", e);
        }
    }

    @Override
    public void deleteDoctor(@NotNull Long id) {
        try (Connection connection = dataSource.getConnection();
             DSLContext context = DSL.using(connection)) {

            int result = context.update(DOCTOR)
                    .set(DOCTOR.DELETED, (byte) 1)
                    .execute();

            if (result == 0) {
                throw new HospitallAppException("Cannot soft delete doctor");
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new HospitallAppException("Cannot soft delete doctor", e);
        }
    }
}

