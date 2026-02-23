package com.erp.studentInformationSystem.service.implementation;

import com.erp.studentInformationSystem.mapper.StandardMapper;
import com.erp.studentInformationSystem.model.Standard;
import com.erp.studentInformationSystem.payload.request.StandardRequestDTO;
import com.erp.studentInformationSystem.payload.response.StandardResponseDTO;
import com.erp.studentInformationSystem.repository.StandardRepository;
import com.erp.studentInformationSystem.service.IStandardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StandardService implements IStandardService {

    private final StandardRepository repository;

    private final StandardMapper mapper;

    @Override
    public StandardResponseDTO createStandard(StandardRequestDTO requestDTO) {
        Standard standard = mapper.toEntity(requestDTO);
        Standard savedStandard = repository.save(standard);

        return mapper.toResponseDTO(savedStandard);
    }

    @Override
    public List<StandardResponseDTO> getAllStandard() {
        return repository.findAll().stream().map(mapper::toResponseDTO).toList();
    }

    @Override
    public StandardResponseDTO getStandardById(String standardId) {
        Standard standard = repository.findById(standardId).orElseThrow();
        return mapper.toResponseDTO(standard);
    }

    @Override
    public StandardResponseDTO updateStandard(String id, StandardRequestDTO requestDTO) {
        return null;
    }
}
