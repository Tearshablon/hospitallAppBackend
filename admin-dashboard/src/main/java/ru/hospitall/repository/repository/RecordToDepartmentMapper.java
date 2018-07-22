package ru.hospitall.repository.repository;

import domain.Department;
import domain.DepartmentName;
import domain.Doctor;
import org.jooq.Record;
import org.jooq.Result;

public class RecordToDepartmentMapper {

    private final Record departmentRecord;
    private final Result<Record> doctorRecords;

    public RecordToDepartmentMapper(Record departmentRecord, Result<Record> doctorRecords) {
        this.departmentRecord = departmentRecord;
        this.doctorRecords = doctorRecords;
    }

    public Department toDomain() {
        return new Department(
                //todo костыль, если не понадобится enum переписать
                Long.valueOf((Integer) departmentRecord.getValue("id")),
                DepartmentName.fromString(departmentRecord.getValue("name").toString()),
                departmentRecord.getValue("description").toString(),
                doctorRecords.into(Doctor.class));
    }
}
