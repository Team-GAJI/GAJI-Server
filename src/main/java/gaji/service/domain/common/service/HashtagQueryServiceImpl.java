package gaji.service.domain.common.service;

import gaji.service.domain.common.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashtagQueryServiceImpl implements HashtagQueryService {

    private final HashtagRepository hashtagRepository;

    @Override
    public boolean ExistHashtagByName(String name) {
        return hashtagRepository.existsByName(name);
    }
}
