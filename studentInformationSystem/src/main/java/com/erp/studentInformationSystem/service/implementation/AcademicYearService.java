package com.erp.studentInformationSystem.service.implementation;

import com.erp.studentInformationSystem.mapper.AcademicYearMapper;
import com.erp.studentInformationSystem.model.AcademicYear;
import com.erp.studentInformationSystem.payload.request.AcademicYearRequestDTO;
import com.erp.studentInformationSystem.payload.response.AcademicYearResponseDTO;
import com.erp.studentInformationSystem.repository.AcademicYearRepository;
import com.erp.studentInformationSystem.service.IAcademicYearService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcademicYearService implements IAcademicYearService {

    private final AcademicYearRepository repository;

    private final AcademicYearMapper mapper;

    @Override
    public AcademicYearResponseDTO createAcademicYear(AcademicYearRequestDTO requestDTO){
        AcademicYear year = mapper.toEntity(requestDTO);
        AcademicYear savedYear = repository.save(year);

        return mapper.toResponseDTO(savedYear);
    }

    @Override
    public List<AcademicYearResponseDTO> getAllAcademicYear() {
        return repository.findAll().stream().map(mapper::toResponseDTO).toList();
    }

    @Override
    public AcademicYearResponseDTO getById(String id) {
        AcademicYear year = repository.findById(id).orElseThrow();
        return mapper.toResponseDTO(year);
    }

    @Override
    public AcademicYearResponseDTO updateAcademicYear(String id, AcademicYearRequestDTO requestDTO) {
        return null;
    }
}
