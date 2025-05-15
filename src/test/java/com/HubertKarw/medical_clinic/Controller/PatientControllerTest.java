package com.HubertKarw.medical_clinic.Controller;

import com.HubertKarw.medical_clinic.Model.CreatePatientCommand;
import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Model.User;
import com.HubertKarw.medical_clinic.Service.PatientJpaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockitoBean
    PatientJpaService patientService;

    @Test
    void shouldGetPatients() throws Exception {
        User user = new User(1L, "1","1");
        List<Patient> patients = List.of(
                new Patient(1L,"1231",user,"123","fn","ln","123", LocalDate.of(2000,1,1)),
                new Patient(2L,"1232",user,"123","fn","ln","123", LocalDate.of(2000,1,1)),
                new Patient(3L,"1233",user,"123","fn","ln","123", LocalDate.of(2000,1,1))
        );
        when(patientService.getPatients(any())).thenReturn(patients);

        mockMvc.perform(MockMvcRequestBuilders.get("/patients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].email").value("1231"))
                .andExpect(jsonPath("$[0].firstName").value("fn"))
                .andExpect(jsonPath("$[0].lastName").value("ln"))
                .andExpect(jsonPath("$[0].phoneNumber").value("123"))
                .andExpect(jsonPath("$[0].birthday").value("2000-01-01"));
    }

    @Test void shouldGetPatient() throws Exception {
        User user = new User(1L, "1","1");
        Patient patient = new Patient(1L,"1231",user,"123","fn","ln","123", LocalDate.of(2000,1,1));
        when(patientService.getPatient("1231")).thenReturn(patient);

        mockMvc.perform(MockMvcRequestBuilders.get("/patients/1231"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("1231"))
                .andExpect(jsonPath("$.firstName").value("fn"))
                .andExpect(jsonPath("$.lastName").value("ln"))
                .andExpect(jsonPath("$.phoneNumber").value("123"))
                .andExpect(jsonPath("$.birthday").value("2000-01-01"));
    }

    @Test
    void shouldDeletePatient() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/patients/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldModifyPatient() throws Exception{
        User user = new User(1L, "1","1");
        Patient modifiedPatient = new Patient(1L,"1231",user,"123","fn","ln","123", LocalDate.of(2000,1,1));
        CreatePatientCommand modifiedCommand =  new CreatePatientCommand("1231",user,"123","fn","ln","123", LocalDate.of(2000,1,1));
        when(patientService.modifyPatient(eq("1231"),any())).thenReturn(modifiedPatient);

        mockMvc.perform(MockMvcRequestBuilders.put("/patients/1231")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(modifiedCommand)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("1231"))
                .andExpect(jsonPath("$.firstName").value("fn"))
                .andExpect(jsonPath("$.lastName").value("ln"))
                .andExpect(jsonPath("$.phoneNumber").value("123"))
                .andExpect(jsonPath("$.birthday").value("2000-01-01"));

    }
}

