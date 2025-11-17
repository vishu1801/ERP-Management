package com.erp.studentInformationSystem.controller;

import com.erp.studentInformationSystem.payload.request.AcademicYearRequestDTO;
import com.erp.studentInformationSystem.payload.request.StandardRequestDTO;
import com.erp.studentInformationSystem.payload.response.AcademicYearResponseDTO;
import com.erp.studentInformationSystem.payload.response.StandardResponseDTO;
import com.erp.studentInformationSystem.service.IAcademicYearService;
import com.erp.studentInformationSystem.service.IStandardService;
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
@RequestMapping("/api/v1/standard")
@RequiredArgsConstructor
public class StandardController {

    private final IStandardService service;

    @PostMapping
    public ResponseEntity<StandardResponseDTO> create(@RequestBody @Valid StandardRequestDTO requestDTO){
        return new ResponseEntity<>(service.createStandard(requestDTO), HttpStatusCode.valueOf(201));
    }

    @GetMapping
    public ResponseEntity<List<StandardResponseDTO>> getAll(){
        return new ResponseEntity<>(service.getAllStandard(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> getById(@PathVariable("id") String standardId){
        return new ResponseEntity<>(service.getStandardById(standardId), HttpStatusCode.valueOf(200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> update(@PathVariable("id") String id, @RequestBody StandardRequestDTO requestDTO){
        return new ResponseEntity<>(service.updateStandard(id, requestDTO), HttpStatusCode.valueOf(201));
    }
}
