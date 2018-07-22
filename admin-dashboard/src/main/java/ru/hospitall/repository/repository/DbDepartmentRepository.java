package ru.hospitall.repository.repository;

import domain.Department;
import domain.DepartmentName;
import domain.HospitallAppException;
import interfaces.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;

import org.jooq.Record;
import org.jooq.Result;
import org.jooq.generate.tables.records.DepartmentRecord;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.jooq.generate.tables.Department.DEPARTMENT;
import static org.jooq.generate.tables.Doctor.DOCTOR;

@Slf4j
public class DbDepartmentRepository implements DepartmentRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public void saveDepartment(@NotNull Department department) {
        if (department.getId() == null) {
            insertDepartment(department);
        } else {



            updateDepartment(department);
        }
    }

    private void insertDepartment(Department department) {
        try (Connection connection = dataSource.getConnection();
             DSLContext context = DSL.using(connection)) {

            int result = context.insertInto(DEPARTMENT)
                    .set(DEPARTMENT.NAME, department.getName().getDepartmentName())
                    .set(DEPARTMENT.DESCRIPTION, department.getDescription())
                    .execute();

            if (result == 0) {
                throw new HospitallAppException("Cannot insert department:");
            }

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new HospitallAppException("Can not insert record into department: ", e);
        }
    }

    private void updateDepartment(Department department) {
        try (Connection connection = dataSource.getConnection();
             DSLContext context = DSL.using(connection)) {

            int result = context.update(DEPARTMENT)
                    .set(DEPARTMENT.NAME, department.getName().getDepartmentName())
                    .set(DEPARTMENT.DESCRIPTION, department.getDescription())
                    .where(DEPARTMENT.ID.eq(department.getId().intValue()))
                    .execute();

            if (result == 0) {
                throw new HospitallAppException("Cannot update department:");
            }

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new HospitallAppException("Can not update record in department: ", e);
        }
    }

    @Override
    public List<Department> findAllDepartmentsWithDoctors() {
        try (Connection connection = dataSource.getConnection();
             DSLContext context = DSL.using(connection)) {
            List<Department> departmentList = new ArrayList<>();

            Map<Record, Result<Record>> departmentsRecordWithDoctorRecords = context
                    .selectFrom(DEPARTMENT.join(DOCTOR)
                            .on(DEPARTMENT.ID.eq(DOCTOR.DEPARTMENT_ID).and(DOCTOR.DELETED.ne((byte) 1))))
                    .where(DEPARTMENT.DELETED.ne((byte) 1))
                    .orderBy(DOCTOR.LAST_NAME.asc())
                    .fetch()
                    .intoGroups(DEPARTMENT.fields());

            for (Map.Entry<Record, Result<Record>> entry : departmentsRecordWithDoctorRecords.entrySet()) {

                Record departmentRecord = entry.getKey();
                Result<Record> doctorRecords = entry.getValue();
                departmentList.add(new RecordToDepartmentMapper(departmentRecord, doctorRecords).toDomain());
            }

            return departmentList;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new HospitallAppException("Cannot find all departments with doctors  ", e);
        }
    }

    @Override
    public @NotNull Department findDepartmentWithoutDoctors(@NotNull Long id) {
        try (Connection connection = dataSource.getConnection();
             DSLContext context = DSL.using(connection)) {

            DepartmentRecord departmentRecord = context.selectFrom(DEPARTMENT)
                    .where(DEPARTMENT.ID.eq(id.intValue()))
                    .fetchOne();

            return new Department(departmentRecord.getId().longValue(),
                    DepartmentName.fromString(departmentRecord.getName()),
                    departmentRecord.getDescription(),
                    null);

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new HospitallAppException("Cannot find department by id", e);
        }
    }

    @Override
    public @NotNull List<Department> findAllDepartmentWithoutDoctors() {
        try (Connection connection = dataSource.getConnection();
             DSLContext context = DSL.using(connection)) {

            return context.
                    selectFrom(DEPARTMENT)
                    .where(DEPARTMENT.DELETED.ne((byte) 1))
                    .stream()
                    .map(departmentRecord ->
                            new Department(departmentRecord.getId().longValue(),
                                    DepartmentName.fromString(departmentRecord.getName()),
                                    departmentRecord.getDescription(),
                                    null))
                    .collect(Collectors.toList());

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new HospitallAppException("Cannot find all department ", e);
        }
    }

    @Override
    public void deleteDepartment(@NotNull Long id) {
        try (Connection connection = dataSource.getConnection();
             DSLContext context = DSL.using(connection)) {

            int result = context.update(DEPARTMENT).
                    set(DEPARTMENT.DELETED, (byte) 1)
                    .execute();

            if (result == 0) {
                throw new HospitallAppException("Cannot safe delete department");
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new HospitallAppException("Cannot safe delete department ", e);
        }
    }
}