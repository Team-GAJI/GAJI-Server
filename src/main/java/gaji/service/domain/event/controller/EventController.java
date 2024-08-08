package gaji.service.domain.event.controller;

import gaji.service.domain.event.dto.response.EventResponse;
import gaji.service.domain.event.service.EventService;
import gaji.service.global.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Event API", description = "일정, ToDo 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @GetMapping("/{date}")
    @Operation(summary = "날짜에 맞는 ToDo 조회 API")
    public BaseResponse<EventResponse> getToDoList(
            @PathVariable("date") DateTime date
    )
    {

        return BaseResponse.onSuccess(eventService.getEvent( , date));
    }

    @PutMapping("/{date}")
    @Operation(summary = "날짜에 맞는 ToDo 생성 API")
    public BaseResponse<> putToDoList(
            @PathVariable("date") DateTime date
    )
    {

        return BaseResponse.onSuccess(eventService.putEvent( , date));
    }

    @PatchMapping("/{date}")
    @Operation(summary = "날짜에 맞는 ToDo 수정 API")
    public BaseResponse<> patchToDoList(
            @PathVariable("date") DateTime date
    )
    {

        return BaseResponse.onSuccess(eventService.patchEvent( , date));
    }

    @DeleteMapping("/{date}")
    @Operation(summary = "날짜에 맞는 ToDo 삭제 API")
    public BaseResponse<> deleteToDoList(
            @PathVariable("date") DateTime date
    )
    {

        return BaseResponse.onSuccess(eventService.deleteEvent( , date));
    }

    @PutMapping("/{eventId}")
    @Operation(summary = "ToDo 완료 API")
    public BaseResponse<> putToDoComplete(
            @PathVariable("eventId") Long eventId
    )
    {

        return BaseResponse.onSuccess(eventService.putEventComplete(eventId));
    }

    @PatchMapping("/{eventId}")
    @Operation(summary = "ToDo 완료 취소 API")
    public BaseResponse<> deleteToDoComplete(
            @PathVariable("eventId") Long eventId
    )
    {

        return BaseResponse.onSuccess(eventService.deleteEventComplete(eventId));
    }
}
