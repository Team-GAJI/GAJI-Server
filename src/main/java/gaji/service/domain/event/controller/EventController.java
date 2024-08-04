package gaji.service.domain.event.controller;

import gaji.service.global.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Event API", description = "일정, ToDo 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @GetMapping("/{date}")
    @Operation(summary = "날짜에 맞는 ToDo 조회 API")
    public BaseResponse<> getToDoList(
            @PathVariable("date") DateTime date
    )
    {

        return BaseResponse.onSuccess(eventService.getToDoList( , date));
    }

    @Operation(summary = "날짜에 맞는 ToDo 생성 API")

    @Operation(summary = "날짜에 맞는 ToDo 수정 API")

    @Operation(summary = "날짜에 맞는 ToDo 삭제 API")

    @Operation(summary = "ToDo 완료 API")

    @Operation(summary = "ToDo 완료 취소 API")
}
