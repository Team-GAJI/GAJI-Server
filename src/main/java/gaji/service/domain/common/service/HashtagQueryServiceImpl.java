package gaji.service.domain.common.service;

import gaji.service.domain.common.entity.SelectHashtag;
import gaji.service.domain.common.repository.HashtagRepository;
import gaji.service.domain.common.repository.SelectHashtagRepository;
import gaji.service.domain.enums.PostTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagQueryServiceImpl implements HashtagQueryService {

    private final HashtagRepository hashtagRepository;
    private final SelectHashtagRepository selectHashtagRepository;

    @Override
    public boolean ExistHashtagByName(String name) {
        return hashtagRepository.existsByName(name);
    }

    @Override
    public List<SelectHashtag> findAllFetchJoinWithCategoryByEntityIdAndPostType(Long entityId, PostTypeEnum postType) {
        return selectHashtagRepository.findAllFetchJoinWithCategoryByEntityIdAndPostType(entityId, postType);
    }

    // TODO: postId에 해당하는 hashtagList 반환해주는 메서드 만들고 HashtagConverter에 DTO로 변환하는 메서드 만들기
}
