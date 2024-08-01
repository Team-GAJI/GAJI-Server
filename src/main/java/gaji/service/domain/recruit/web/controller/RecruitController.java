package gaji.service.domain.recruit.web.controller;

import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.entity.RecruitPost;
import gaji.service.domain.recruit.service.RecruitCommandService;
import gaji.service.domain.recruit.service.RecruitCommandServiceImpl;
import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.converter.RoomConverter;
import gaji.service.domain.room.service.RoomCommandServiceImpl;
import gaji.service.domain.room.web.dto.RoomRequestDto;
import gaji.service.domain.room.web.dto.RoomResponseDto;
import gaji.service.domain.studyMate.Assignment;
import gaji.service.global.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studyRecruitPosts")
public class RecruitController {

    private final RecruitCommandService recruitCommandService;

    @PostMapping("/{roomId}")
    @Operation(summary = "스터디 모집 게시글 생성 API", description = "스터디 모집 게시글을 생성하는 API입니다.")
    public BaseResponse<RecruitResponseDTO.CreateRecruitDTO> createRecruitPost(@RequestBody @Valid RecruitRequestDTO.CreateRecruitDTO request, @PathVariable Long roomId, @RequestParam("user") Long userId) {
        RecruitPost newRecruitPost = recruitCommandService.createRecruitPost(request, roomId, userId);
        return BaseResponse.onSuccess(RecruitConverter.toResponseDTO(newRecruitPost));
    }
}

