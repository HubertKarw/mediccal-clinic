package com.HubertKarw.medical_clinic.Controller;

import com.HubertKarw.medical_clinic.Model.CreateInstitutionCommand;
import com.HubertKarw.medical_clinic.Model.Institution;
import com.HubertKarw.medical_clinic.Service.InstitutionJpaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InstitutionControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockitoBean
    InstitutionJpaService institutionService;

    @Test
    void shouldGetInstitutions() throws Exception{
        List<Institution> institutions = List.of(
                new Institution(1L, "name", "loc", "1000","addr",1),
                new Institution(1L, "name", "loc", "1000","addr",1),
                new Institution(1L, "name", "loc", "1000","addr",1)
        );
        when(institutionService.getInstitutions(any())).thenReturn(institutions);

        mockMvc.perform(MockMvcRequestBuilders.get("/institution"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("name"))
                .andExpect(jsonPath("$[0].location").value("loc"))
                .andExpect(jsonPath("$[0].postalCode").value("1000"))
                .andExpect(jsonPath("$[0].streetAddress").value("addr"))
                .andExpect(jsonPath("$[0].streetNumber").value("1"));
    }

    @Test
    void shouldGetInstitution() throws Exception{
        Institution institution = new Institution(1L, "name", "loc", "1000","addr",1);
        when(institutionService.getInstitution(any())).thenReturn(institution);

        mockMvc.perform(MockMvcRequestBuilders.get("/institution/name"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.location").value("loc"))
                .andExpect(jsonPath("$.postalCode").value("1000"))
                .andExpect(jsonPath("$.streetAddress").value("addr"))
                .andExpect(jsonPath("$.streetNumber").value("1"));
    }

    @Test
    void shouldDeleteInstitution() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/institution/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldModifyInstitution() throws Exception{
        Institution institution = new Institution(1L, "name", "loc", "1000","addr",1);
        CreateInstitutionCommand modifyCommand = new CreateInstitutionCommand( "name", "loc", "1000","addr",1);
        when(institutionService.modifyInstitution(eq("name"),any())).thenReturn(institution);
        mockMvc.perform(MockMvcRequestBuilders.put("/institution/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(modifyCommand)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.location").value("loc"))
                .andExpect(jsonPath("$.postalCode").value("1000"))
                .andExpect(jsonPath("$.streetAddress").value("addr"))
                .andExpect(jsonPath("$.streetNumber").value("1"));
    }

}
