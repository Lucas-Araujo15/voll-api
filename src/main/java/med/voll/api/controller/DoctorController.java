package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.doctor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/doctor")
@SecurityRequirement(name = "bearer-key")
public class DoctorController {
    @Autowired
    private DoctorRepository doctorRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid DoctorRegisterData data, UriComponentsBuilder uriBuilder) {
        Doctor doctor = new Doctor(data);

        doctorRepository.save(doctor);

        URI uri = uriBuilder.path("/doctor/{id}").buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailedDoctorData(doctor));
    }

    @GetMapping
    public ResponseEntity<Page<DoctorListData>> list(@PageableDefault(size = 10, page = 0, sort = "name", direction = Sort.Direction.ASC) Pageable pagination) {
        Page<DoctorListData> page = doctorRepository.findAllByActiveTrue(pagination).map(DoctorListData::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailedDoctorData> detail(@PathVariable("id") Long id) {
        Doctor doctor = doctorRepository.getReferenceById(id);
        return ResponseEntity.ok(new DetailedDoctorData(doctor));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DetailedDoctorData> update(@RequestBody @Valid DoctorUpdateData data) {
        Doctor doctor = doctorRepository.getReferenceById(data.id());
        doctor.updateInformation(data);

        return ResponseEntity.ok(new DetailedDoctorData(doctor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Doctor doctor = doctorRepository.getReferenceById(id);
        doctor.delete();

        return ResponseEntity.noContent().build();
    }
}
