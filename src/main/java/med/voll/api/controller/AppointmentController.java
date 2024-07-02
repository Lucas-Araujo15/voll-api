package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.appointment.AppointmentCancellationData;
import med.voll.api.domain.appointment.AppointmentScheduling;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import med.voll.api.domain.appointment.DetailedAppointmentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentScheduling appointmentScheduling;

    @PostMapping
    @Transactional
    public ResponseEntity<?> schedule(@RequestBody @Valid AppointmentSchedulingData data) {
        DetailedAppointmentData detailedAppointmentData = this.appointmentScheduling.schedule(data);
        return ResponseEntity.ok(detailedAppointmentData);
    }

    @PutMapping("/cancel")
    @Transactional
    public ResponseEntity<?> cancel(@RequestBody @Valid AppointmentCancellationData data) {
        this.appointmentScheduling.cancel(data);
        return ResponseEntity.noContent().build();
    }
}
