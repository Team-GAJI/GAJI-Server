package gaji.service.domain.room.service;

import gaji.service.domain.room.entity.Material;
import gaji.service.domain.room.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialCommandServiceImpl implements MaterialCommandService {

    private MaterialRepository materialRepository;

    @Override
    public void saveMaterial(Material material) {
        materialRepository.save(material);
    }
}
