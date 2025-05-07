package com.HubertKarw.medical_clinic.Controller;

import com.HubertKarw.medical_clinic.Model.CreateDoctorCommand;
import com.HubertKarw.medical_clinic.Model.Doctor;
import com.HubertKarw.medical_clinic.Model.DoctorDTO;
import com.HubertKarw.medical_clinic.Model.PatientDTO;
import com.HubertKarw.medical_clinic.Service.DoctorJpaService;;
import com.HubertKarw.medical_clinic.mappers.DoctorStructMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@Tag(name = "Doctor", description = "Doctor operations")
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorJpaService service;
    private final DoctorStructMapper mapper;

    @Operation(summary = "Get all doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found patients", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Doctor.class))})})
    @GetMapping
    public List<DoctorDTO> getDoctors(Pageable pageable) {
//        zostawione zeby wiedziec jak dziala
//    public List<DoctorDTO> getDoctors(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "0") int size) {
//        Pageable pageable = PageRequest.of(page,size);
        return service.getDoctors(pageable).stream()
                .map(mapper::mapToDTO)
                .collect(toList());
    }

    @GetMapping("/{email}")
    public DoctorDTO getDoctor(@PathVariable("email") String email) {
        return mapper.mapToDTO(service.getDoctor(email));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDTO addDoctor(
            @RequestBody CreateDoctorCommand command
    ) {
        return mapper.mapToDTO(service.addDoctor(mapper.mapToDoctor(command)));
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeDoctor(@PathVariable("email") String email) {
        service.removeDoctor(email);
    }

    @PutMapping("/{email}")
    public DoctorDTO modifyDoctor(@PathVariable("email") String email, @RequestBody CreateDoctorCommand command) {
        return mapper.mapToDTO(service.modifyDoctor(email, mapper.mapToDoctor(command)));
    }

    @PatchMapping("/{email}/institutions/{name}")
    public DoctorDTO assignToInstitution(@PathVariable("email") String email, @PathVariable("name") String name) {
        return mapper.mapToDTO(service.assignToInstitution(email, name));
    }

}
