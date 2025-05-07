package com.HubertKarw.medical_clinic.Controller;

import com.HubertKarw.medical_clinic.Model.*;
import com.HubertKarw.medical_clinic.Service.PatientJpaService;
import com.HubertKarw.medical_clinic.Service.PatientMapper;
import com.HubertKarw.medical_clinic.Service.PatientService;
import com.HubertKarw.medical_clinic.Service.PatientStructMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Patient", description = "Patient operations")
@RequestMapping("/patients")
public class PatientController {

    private final PatientJpaService patientService;
    private final PatientStructMapper mapper;

    @Operation(summary = "Get all patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found patients", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PatientDTO.class))})})
    @GetMapping
    public List<PatientDTO> getPatients(@RequestParam(name = "page", defaultValue = "0") int page) {
        int size = 2;
        Pageable pageable = PageRequest.of(page,size);
        return patientService.getPatients(pageable).stream()
                .map(mapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get patient by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found patient", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PatientDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))})
    })
    @GetMapping("/{email}")
    public PatientDTO getPatient(@PathVariable("email") String email) {
        return mapper.mapToDTO(patientService.getPatient(email));
    }

    @Operation(summary = "Create patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created patient", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PatientDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Patient is not valid", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))})
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDTO addPatient(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Patient to create", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CreatePatientCommand.class),
                    examples = @ExampleObject(value = "{ \"Email\": \"email\", \"User\": \"User\", \"idCardNo\": \"1234\"" +
                            ", \"firstName\": \"John\", \"lastName\": \"Doe\",  \"phoneNumber\": \"123456789\",  \"birthday\": \"1999-01-01\"}")))
                                 @RequestBody CreatePatientCommand command) {
        return mapper.mapToDTO(patientService.addPatient(mapper.mapToPatient(command)));
    }

    @Operation(summary = "Remove patient by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed patient", content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))})
    })
    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePatient(@PathVariable("email") String email) {
        patientService.removePatient(email);
    }

    @Operation(summary = "Edit patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edited patient information", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PatientDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))}),
            @ApiResponse(responseCode = "400", description = "Patient is not valid", content = @Content)
    })
    @PutMapping("/{email}")
    public PatientDTO modifyPatient(@PathVariable("email") String email,
                                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                            description = "Patient to create", required = true,
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = CreatePatientCommand.class),
                                                    examples = @ExampleObject(value = "{ \"email\": \"email\", \"user\": \"User\", \"idCardNo\": \"1234\"" +
                                                            ", \"firstName\": \"John\", \"lastName\": \"Doe\",  \"phoneNumber\": \"123456789\",  \"birthday\": \"1999-01-01\"}")))
                                    @RequestBody CreatePatientCommand patient) {
        return mapper.mapToDTO(patientService.modifyPatient(email, mapper.mapToPatient(patient)));
    }
//    @Operation(summary = "edit patients password")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Edited patient password",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = PatientDTO.class))}),
//            @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content),
//            @ApiResponse(responseCode = "400", description = "Patient is not valid", content = @Content)
//    })
//    @PatchMapping("/{email}")
//    public PatientDTO setPassword(@PathVariable("email") String email,
//                                  @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                                          description = "Patient to create", required = true,
//                                          content = @Content(mediaType = "application/json",
//                                                  schema = @Schema(implementation = CreatePatientCommand.class),
//                                                  examples = @ExampleObject(value = "{ \"Email\": \"email\", \"User\": \"User\", \"idCardNO\": \"1234\"" +
//                                                          ", \"firstName\": \"John\", \"lastName\": \"Doe\",  \"phoneNumber\": \"123456789\",  \"birthday\": \"1999-01-01\"}")))
//                                  @RequestBody Password password) {
//        return mapper.mapToDTO(patientService.setPassword(email, password));
//    }
}
