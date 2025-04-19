package com.HubertKarw.medical_clinic.Controller;

import com.HubertKarw.medical_clinic.Model.CreateInstitutionCommand;
import com.HubertKarw.medical_clinic.Model.InstitutionDTO;
import com.HubertKarw.medical_clinic.Service.InstitutionJpaService;
import com.HubertKarw.medical_clinic.mappers.InstitutionStructMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@Tag(name = "Institution", description = "Institution operations")
@RequestMapping("/institution")
public class InstitutionController {
    private final InstitutionJpaService service;
    private final InstitutionStructMapper mapper;

    @GetMapping
    public List<InstitutionDTO> getInsitutions() {
        return service.getInstitutions().stream()
                .map(mapper::mapToDTO)
                .collect(toList());
    }

    @GetMapping("/{name}")
    public InstitutionDTO getInstitution(@PathVariable String name) {
        return mapper.mapToDTO(service.getInstitution(name));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InstitutionDTO addInstitution(@RequestBody CreateInstitutionCommand command) {
        return mapper.mapToDTO(service.addInstitution(mapper.mapToInstitution(command)));
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeInstitution(@PathVariable String name) {
        service.removeInstitution(name);
    }

    @PutMapping("/{name}")
    public InstitutionDTO modifyInstitution(@PathVariable String name, @RequestBody CreateInstitutionCommand command) {
        return mapper.mapToDTO(service.modifyInstitution(name, mapper.mapToInstitution(command)));
    }
}
