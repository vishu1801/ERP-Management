package com.erp.studentInformationSystem.controller;

import com.erp.studentInformationSystem.payload.request.CasteRequestDTO;
import com.erp.studentInformationSystem.payload.request.ReligionRequestDTO;
import com.erp.studentInformationSystem.payload.response.CasteResponseDTO;
import com.erp.studentInformationSystem.payload.response.ReligionResponseDTO;
import com.erp.studentInformationSystem.service.ICasteService;
import com.erp.studentInformationSystem.service.IReligionService;
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
@RequestMapping("/api/v1/religion")
@RequiredArgsConstructor
public class ReligionController {

    private final IReligionService service;

    @PostMapping
    public ResponseEntity<ReligionResponseDTO> create(@RequestBody @Valid ReligionRequestDTO requestDTO){
        return new ResponseEntity<>(service.createReligion(requestDTO), HttpStatusCode.valueOf(201));
    }

    @GetMapping
    public ResponseEntity<List<ReligionResponseDTO>> getAll(){
        return new ResponseEntity<>(service.getAllReligion(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReligionResponseDTO> getById(@PathVariable("id") String religionId){
        return new ResponseEntity<>(service.getReligionById(religionId), HttpStatusCode.valueOf(200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReligionResponseDTO> update(@PathVariable("id") String id, @RequestBody ReligionRequestDTO requestDTO){
        return new ResponseEntity<>(service.updateReligion(id, requestDTO), HttpStatusCode.valueOf(201));
    }
}
