package com.HubertKarw.medical_clinic.Controller;

import com.HubertKarw.medical_clinic.Model.CreateDoctorCommand;
import com.HubertKarw.medical_clinic.Model.Doctor;
import com.HubertKarw.medical_clinic.Model.Institution;
import com.HubertKarw.medical_clinic.Model.User;
import com.HubertKarw.medical_clinic.Service.DoctorJpaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockitoBean
    DoctorJpaService doctorService;

    @Test
    void shouldGetDoctors() throws Exception{
        User user = new User(1L, "1","1");
        List<Doctor> doctors = List.of(
                new Doctor(1L,"123",user,"1","1","spec",new ArrayList<>()),
                new Doctor(1L,"123",user,"1","1","spec",new ArrayList<>()),
                new Doctor(1L,"123",user,"1","1","spec",new ArrayList<>())
        );
        when(doctorService.getDoctors(any())).thenReturn(doctors);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctor"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].email").value("123"))
                .andExpect(jsonPath("$[0].firstName").value("1"))
                .andExpect(jsonPath("$[0].lastName").value("1"))
                .andExpect(jsonPath("$[0].specialization").value("spec"))
                .andExpect(jsonPath("$[0].institutions").isArray());
    }

    @Test
    void shouldGetDoctor() throws Exception{
        User user = new User(1L, "1","1");
        Doctor doctor = new Doctor(1L,"123",user,"1","1","spec",new ArrayList<>());
        when(doctorService.getDoctor("123")).thenReturn(doctor);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctor/123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("123"))
                .andExpect(jsonPath("$.firstName").value("1"))
                .andExpect(jsonPath("$.lastName").value("1"))
                .andExpect(jsonPath("$.specialization").value("spec"))
                .andExpect(jsonPath("$.institutions").isArray());
    }

    @Test
    void shouldDeleteDoctor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/doctor/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldModifyDoctor() throws Exception{
        User user = new User(1L, "1","1");
        Doctor modifiedDoctor = new Doctor(1L,"123",user,"1","1","spec",new ArrayList<>());
        CreateDoctorCommand modifiedCommand = new CreateDoctorCommand(1L,"123",user,"1","1","spec",new ArrayList<>());
        when(doctorService.modifyDoctor(eq("123"),any())).thenReturn(modifiedDoctor);

        mockMvc.perform(MockMvcRequestBuilders.put("/doctor/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(modifiedCommand)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("123"))
                .andExpect(jsonPath("$.firstName").value("1"))
                .andExpect(jsonPath("$.lastName").value("1"))
                .andExpect(jsonPath("$.specialization").value("spec"))
                .andExpect(jsonPath("$.institutions").isArray());

    }


    @Test
    void shouldAssignToInstitution() throws Exception{
        User user = new User(1L, "1","1");
        Institution institution = new Institution(1L, "name", "loc", "1000","addr",1);
        Doctor doctor = new Doctor(1L,"123",user,"1","1","spec",List.of(institution));
        when(doctorService.assignToInstitution(any(),any())).thenReturn(doctor);

        mockMvc.perform(MockMvcRequestBuilders.patch("/doctor/123/institutions/name"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("123"))
                .andExpect(jsonPath("$.firstName").value("1"))
                .andExpect(jsonPath("$.lastName").value("1"))
                .andExpect(jsonPath("$.specialization").value("spec"))
                .andExpect(jsonPath("$.institutions").isArray())
                .andExpect(jsonPath("$.institutions[0].name").value("name"))
                .andExpect(jsonPath("$.institutions[0].location").value("loc"));
    }
}
