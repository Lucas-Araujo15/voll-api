package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.doctor.Doctor;
import med.voll.api.doctor.DoctorListData;
import med.voll.api.doctor.DoctorRegisterData;
import med.voll.api.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorRepository doctorRepository;

    @PostMapping
    @Transactional
    public void create(@RequestBody @Valid DoctorRegisterData data) {
        doctorRepository.save(new Doctor(data));
    }

    @GetMapping
    public ResponseEntity<Page<DoctorListData>> list(Pageable pagination) {
        return ResponseEntity.ok(doctorRepository.findAll(pagination).map(DoctorListData::new));
    }
}
