package com.erp.studentInformationSystem.controller;

import com.erp.studentInformationSystem.payload.request.AcademicYearRequestDTO;
import com.erp.studentInformationSystem.payload.request.CasteRequestDTO;
import com.erp.studentInformationSystem.payload.response.AcademicYearResponseDTO;
import com.erp.studentInformationSystem.payload.response.CasteResponseDTO;
import com.erp.studentInformationSystem.service.IAcademicYearService;
import com.erp.studentInformationSystem.service.ICasteService;
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
@RequestMapping("/api/v1/caste")
@RequiredArgsConstructor
public class CasteController {

    private final ICasteService service;

    @PostMapping
    public ResponseEntity<CasteResponseDTO> create(@RequestBody @Valid CasteRequestDTO requestDTO){
        return new ResponseEntity<>(service.createCaste(requestDTO), HttpStatusCode.valueOf(201));
    }

    @GetMapping
    public ResponseEntity<List<CasteResponseDTO>> getAll(){
        return new ResponseEntity<>(service.getAllCaste(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CasteResponseDTO> getById(@PathVariable("id") String casteId){
        return new ResponseEntity<>(service.getCasteById(casteId), HttpStatusCode.valueOf(200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CasteResponseDTO> update(@PathVariable("id") String id, @RequestBody CasteRequestDTO requestDTO){
        return new ResponseEntity<>(service.updateCaste(id, requestDTO), HttpStatusCode.valueOf(201));
    }
}
