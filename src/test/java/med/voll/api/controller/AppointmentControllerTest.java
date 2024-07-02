package med.voll.api.controller;

import med.voll.api.domain.appointment.AppointmentScheduling;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import med.voll.api.domain.appointment.DetailedAppointmentData;
import med.voll.api.domain.doctor.Specialty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AppointmentSchedulingData> appointmentSchedulingDataJson;

    @Autowired
    private JacksonTester<DetailedAppointmentData> detailedAppointmentDataJson;

    @MockBean
    private AppointmentScheduling appointmentScheduling;

    @Test()
    @DisplayName("should return status code 400 when the information is invalid")
    @WithMockUser
    void scheduleScenario1() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(post("/appointment"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test()
    @DisplayName("should return status code 200 when the information is valid")
    @WithMockUser
    void scheduleScenario2() throws Exception {
        LocalDateTime date = LocalDateTime.now().plusHours(1);
        Specialty specialty = Specialty.CARDIOLOGY;

        DetailedAppointmentData detailedAppointmentData = new DetailedAppointmentData(null, 2L, 5L, date);

        when(appointmentScheduling.schedule(any())).thenReturn(detailedAppointmentData);

        MockHttpServletResponse response = mvc
                .perform(
                        post("/appointment")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(appointmentSchedulingDataJson.write(
                                        new AppointmentSchedulingData(2L, 5L, date, specialty)
                                ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        String expectedJson = detailedAppointmentDataJson.write(
                detailedAppointmentData
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}