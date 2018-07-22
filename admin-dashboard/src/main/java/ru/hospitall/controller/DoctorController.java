package ru.hospitall.controller;

import domain.Doctor;
import interfaces.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/doctor")
@RestController
public class DoctorController {

    private DoctorRepository doctorRepository;

    @Autowired
    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping("/find-by-id/{id}")
    public Doctor findDoctor(@PathVariable("id") String id) {
        return doctorRepository.findDoctor(Long.valueOf(id));
    }

    @PostMapping("/save-doctor")
    public void saveDoctor(@RequestParam(value = "departmentId") String departmentId, @RequestBody Doctor doctor) {
        doctorRepository.saveDoctor(doctor, Long.valueOf(departmentId));
    }

    @GetMapping("/find-all-doctors")
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAllDoctors();
    }

    @DeleteMapping("/delete-by-id/{id}")
    public void deleteDoctor(@PathVariable("id") String id) {
        doctorRepository.deleteDoctor(Long.valueOf(id));
    }
}
