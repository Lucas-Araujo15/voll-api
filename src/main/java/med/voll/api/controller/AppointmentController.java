package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import med.voll.api.domain.appointment.DetailedAppointmentData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @PostMapping
    @Transactional
    public ResponseEntity<?> schedule(@RequestBody @Valid AppointmentSchedulingData data) {
        System.out.println(data);
        return ResponseEntity.ok(new DetailedAppointmentData(null, null, null, null));
    }
}
