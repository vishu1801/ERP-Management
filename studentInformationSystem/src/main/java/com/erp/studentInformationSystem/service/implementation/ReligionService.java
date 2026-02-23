package com.erp.studentInformationSystem.service.implementation;

import com.erp.studentInformationSystem.mapper.ReligionMapper;
import com.erp.studentInformationSystem.model.Religion;
import com.erp.studentInformationSystem.payload.request.ReligionRequestDTO;
import com.erp.studentInformationSystem.payload.response.ReligionResponseDTO;
import com.erp.studentInformationSystem.repository.ReligionRepository;
import com.erp.studentInformationSystem.service.IReligionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReligionService implements IReligionService {

    private final ReligionRepository repository;

    private final ReligionMapper mapper;

    @Override
    public ReligionResponseDTO updateReligion(String id, ReligionRequestDTO requestDTO) {
        return null;
    }

    @Override
    public ReligionResponseDTO getReligionById(String religionId) {
        Religion religion = repository.findById(religionId).orElseThrow();
        return mapper.toResponseDTO(religion);
    }

    @Override
    public List<ReligionResponseDTO> getAllReligion() {
        return repository.findAll().stream().map(mapper::toResponseDTO).toList();
    }

    @Override
    public ReligionResponseDTO createReligion(ReligionRequestDTO requestDTO) {
        Religion religion = mapper.toEntity(requestDTO);
        Religion savedReligion = repository.save(religion);

        return mapper.toResponseDTO(savedReligion);
    }
}
