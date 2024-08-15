package gaji.service.domain.event.controller;

import gaji.service.domain.event.domain.Event;
import gaji.service.domain.event.dto.request.EventInfoRequest;
import gaji.service.domain.event.dto.response.EventInfoListResponse;
import gaji.service.domain.event.dto.response.EventIdResponse;
import gaji.service.domain.event.service.EventService;
import gaji.service.domain.user.entity.User;
import gaji.service.global.auth.CurrentUser;
import gaji.service.global.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Tag(name = "Event API", description = "일정, ToDo 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @GetMapping("/{userId}/{date}")
    @Operation(summary = "날짜에 맞는 ToDo 조회 API",
            description = "해당 날짜에 맞는 ToDo를 조회합니다. 내가 아닌 다른 사람의 일정도 조회 가능합니다.")
    public BaseResponse<EventInfoListResponse> getToDoList(
            @PathVariable("date") LocalDate date,
            @PathVariable("userId") Long userId
    )
    {
        return BaseResponse.onSuccess(eventService.getEventList(date, userId));
    }

    @PutMapping("/{userId}/{date}")
    @Operation(summary = "날짜에 맞는 ToDo 생성 API",
            description = "해당 날짜에 맞는 ToDo를 생성합니다")
    public BaseResponse<EventIdResponse> putToDoList(
            @CurrentUser User user,
            @PathVariable("date") LocalDate date,
            @PathVariable("userId") Long userId,
            @RequestBody EventInfoRequest request
            )
    {
        return BaseResponse.onSuccess(
                new EventIdResponse(eventService.putEvent(date, userId, user.getId() ,request))
        );
    }

    @PatchMapping("/{eventId}")
    @Operation(summary = "ToDo 수정 API")
    public BaseResponse<EventIdResponse> patchToDoList(
            @CurrentUser User user,
            @PathVariable("eventId")Long eventId,
            @RequestBody EventInfoRequest request
    )
    {

        return BaseResponse.onSuccess(
                new EventIdResponse( eventService.patchEvent(eventId, user.getId(), request))
        );
    }

    @DeleteMapping("/{eventId}")
    @Operation(summary = "ToDo 삭제 API")
    public BaseResponse<EventIdResponse> deleteToDoList(
            @CurrentUser User user,
            @PathVariable("eventId")Long eventId
    )
    {

        return BaseResponse.onSuccess(
                new EventIdResponse(eventService.deleteEvent(eventId, user.getId()))
        );
    }

    @PutMapping("/{eventId}")
    @Operation(summary = "ToDo 완료 API")
    public BaseResponse<EventIdResponse> putToDoComplete(
            @CurrentUser User user,
            @PathVariable("eventId") Long eventId
    )
    {

        return BaseResponse.onSuccess(
                new EventIdResponse(eventService.putEventComplete(eventId, user.getId()))
        );
    }

    @PatchMapping("/{eventId}")
    @Operation(summary = "ToDo 완료 취소 API")
    public BaseResponse<EventIdResponse> deleteToDoComplete(
            @CurrentUser User user,
            @PathVariable("eventId") Long eventId
    )
    {

        return BaseResponse.onSuccess(
                new EventIdResponse(eventService.deleteEventComplete(eventId, user.getId()))
        );
    }
}
