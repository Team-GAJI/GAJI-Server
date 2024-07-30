package gaji.service.domain.room.web.controller;

import gaji.service.domain.room.converter.RoomConverter;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.service.RoomCommandService;
import gaji.service.domain.room.web.dto.RoomRequestDTO;
import gaji.service.domain.room.web.dto.RoomResponseDTO;
import gaji.service.global.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoomRestController {

    private final RoomCommandService roomCommandService;

    @PostMapping(value = "/studys")
    @Operation(summary = "스터디 만들기 API", description = "스터디를 만드는 API입니다. 스터디 인원 및 비어있는 값을 검증합니다 ")
    public BaseResponse<RoomResponseDTO.CreateStudyDTO> createStudy(@RequestBody @Valid RoomRequestDTO.CreateStudyDTO request) {
        Room newRoom = roomCommandService.createStudy(request);
        return BaseResponse.onSuccess(RoomConverter.toResponseDTO(newRoom));
    }
}
