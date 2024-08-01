package gaji.service.domain.recruit.web.controller;

import gaji.service.domain.recruit.converter.RecruitConverter;
import gaji.service.domain.recruit.service.RecruitCommandService;
import gaji.service.domain.recruit.service.RecruitQueryService;
import gaji.service.domain.recruit.web.dto.RecruitRequestDTO;
import gaji.service.domain.recruit.web.dto.RecruitResponseDTO;
import gaji.service.domain.room.entity.Room;
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
    private final RecruitQueryService recruitQueryService;

    @PostMapping("")
    @Operation(summary = "스터디 모집 게시글 생성 API", description = "스터디 모집 게시글을 생성하는 API입니다.")
    public BaseResponse<RecruitResponseDTO.CreateRoomDTO> createRoom(@RequestBody @Valid RecruitRequestDTO.CreateRoomDTO request, @RequestParam("user") Long userId) {
        Room newRoom = recruitCommandService.createRoom(request, userId);
        return BaseResponse.onSuccess(RecruitConverter.toResponseDTO(newRoom));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "스터디 정보 상세 조회 API", description = "스터디 상세 정보를 조회하는 API입니다.")
    public BaseResponse<RecruitResponseDTO.studyDetailDTO> getStudyDetail(@PathVariable Long postId) {
        RecruitResponseDTO.studyDetailDTO responseDTO = recruitQueryService.getStudyDetail(postId);
        return BaseResponse.onSuccess(responseDTO);
    }
}

