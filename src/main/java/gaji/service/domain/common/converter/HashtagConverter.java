package gaji.service.domain.common.converter;

import gaji.service.domain.common.entity.Hashtag;
import gaji.service.domain.common.entity.SelectHashtag;
import gaji.service.domain.common.web.dto.HashtagResponseDTO;
import gaji.service.domain.enums.PostTypeEnum;

import java.util.List;
import java.util.stream.Collectors;


public class HashtagConverter {

    public static Hashtag toHashtag(String name) {
        return Hashtag.builder()
                .name(name)
                .build();

    }

    public static List<Hashtag> toHashtagList(List<String> hashtagList) {
        return hashtagList.stream()
                .map(hashtag ->
                        Hashtag.builder()
                                .name(hashtag)
                                .build()
                ).collect(Collectors.toList());
    }

    public static List<SelectHashtag> toSelectHashtagList(List<Hashtag> hashtagList, Long entityId, PostTypeEnum type) {
        return hashtagList.stream()
                .map(hashtag ->
                        SelectHashtag.builder()
                                .hashtag(hashtag)
                                .entityId(entityId)
                                .type(type)
                                .build()
                ).collect(Collectors.toList());
    }

    public static HashtagResponseDTO.BaseResponseDTO toBaseResponseDTO(SelectHashtag selectHashtag) {
        return HashtagResponseDTO.BaseResponseDTO.builder()
                .hashtagId(selectHashtag.getHashtag().getId())
                .hashtagName(selectHashtag.getHashtag().getName())
                .build();
    }

    public static List<HashtagResponseDTO.BaseResponseDTO> toBaseResponseDTOList(List<SelectHashtag> selectHashtagList) {
        return selectHashtagList.stream()
                .map(HashtagConverter::toBaseResponseDTO)
                .collect(Collectors.toList());
    }
}
