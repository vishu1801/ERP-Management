package com.erp.studentInformationSystem.service.implementation;

import com.erp.studentInformationSystem.mapper.CasteMapper;
import com.erp.studentInformationSystem.model.Caste;
import com.erp.studentInformationSystem.payload.request.CasteRequestDTO;
import com.erp.studentInformationSystem.payload.response.CasteResponseDTO;
import com.erp.studentInformationSystem.repository.CasteRepository;
import com.erp.studentInformationSystem.service.ICasteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CasteService implements ICasteService {

    private final CasteRepository repository;

    private final CasteMapper mapper;

    @Override
    public CasteResponseDTO createCaste(CasteRequestDTO requestDTO) {
        Caste caste = mapper.toEntity(requestDTO);
        Caste savedCaste = repository.save(caste);

        return mapper.toResponseDTO(savedCaste);
    }

    @Override
    public CasteResponseDTO getCasteById(String casteId) {
        Caste caste = repository.findById(casteId).orElseThrow();
        return mapper.toResponseDTO(caste);
    }

    @Override
    public List<CasteResponseDTO> getAllCaste() {
        return repository.findAll().stream().map(mapper::toResponseDTO).toList();
    }

    @Override
    public CasteResponseDTO updateCaste(String id, CasteRequestDTO requestDTO) {
        return null;
    }
}
