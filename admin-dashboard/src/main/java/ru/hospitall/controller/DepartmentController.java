package ru.hospitall.controller;

import domain.Department;
import interfaces.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @PostMapping("/save-department")
    public void saveDepartment(@RequestBody Department department) {
        departmentRepository.saveDepartment(department);
    }

    @GetMapping("/find-department-by-id/{id}")
    public Department findDepartment(@PathVariable("id") String id) {
        return departmentRepository.findDepartmentWithoutDoctors(Long.valueOf(id));
    }

    @GetMapping("/find-all-departments-without-doctors")
    public List<Department> findAllDepartmentsWithoutDoctors() {
        return departmentRepository.findAllDepartmentWithoutDoctors();
    }

    @DeleteMapping("/delete-by-id/{id}")
    public void deleteDepartment(@PathVariable("id") String id) {
        departmentRepository.deleteDepartment(Long.valueOf(id));
    }

    @GetMapping("/find-all-departments-with-doctors")
    public List<Department> findAllDepartmentsWithDoctors() {
        return departmentRepository.findAllDepartmentsWithDoctors();
    }
}

