package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.patient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/patient")
@SecurityRequirement(name = "bearer-key")
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid PatientRegisterData data, UriComponentsBuilder uriBuilder) {
        Patient patient = new Patient(data);

        patientRepository.save(patient);

        URI uri = uriBuilder.path("/patient/{id}").buildAndExpand(patient.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailedPatientData(patient));
    }

    @GetMapping
    public ResponseEntity<Page<PatientListData>> list(@PageableDefault(size = 10, page = 0, sort = "name", direction = Sort.Direction.ASC) Pageable pagination) {
        Page<PatientListData> page = patientRepository.findAllByActiveTrue(pagination).map(PatientListData::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailedPatientData> detail(@PathVariable("id") Long id) {
        Optional<Patient> patient = patientRepository.findByIdAndActiveTrue(id);

        return patient.map(value -> ResponseEntity.ok(new DetailedPatientData(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DetailedPatientData> update(@RequestBody @Valid PatientUpdateData data) {
        Optional<Patient> patient = patientRepository.findByIdAndActiveTrue(data.id());

        if (patient.isPresent()) {
            patient.get().updateInformation(data);
            return ResponseEntity.ok(new DetailedPatientData(patient.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Patient> patient = patientRepository.findByIdAndActiveTrue(id);

        patient.ifPresent(Patient::delete);

        return ResponseEntity.noContent().build();
    }
}
