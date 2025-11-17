package com.erp.studentInformationSystem.controller;

import com.erp.studentInformationSystem.payload.request.AcademicYearRequestDTO;
import com.erp.studentInformationSystem.payload.response.AcademicYearResponseDTO;
import com.erp.studentInformationSystem.service.IAcademicYearService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/academic-year")
@RequiredArgsConstructor
public class AcademicYearController {

    private final IAcademicYearService service;

    @PostMapping
    public ResponseEntity<AcademicYearResponseDTO> create(@RequestBody @Valid AcademicYearRequestDTO requestDTO){
        return new ResponseEntity<>(service.createAcademicYear(requestDTO), HttpStatusCode.valueOf(201));
    }

    @GetMapping
    public ResponseEntity<List<AcademicYearResponseDTO>> getAll(){
        return new ResponseEntity<>(service.getAllAcademicYear(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademicYearResponseDTO> getById(@PathVariable("id") String yearId){
        return new ResponseEntity<>(service.getById(yearId), HttpStatusCode.valueOf(200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicYearResponseDTO> update(@PathVariable("id") String id, @RequestBody AcademicYearRequestDTO requestDTO){
        return new ResponseEntity<>(service.updateAcademicYear(id, requestDTO), HttpStatusCode.valueOf(201));
    }

}
