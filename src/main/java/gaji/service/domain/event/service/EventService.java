package gaji.service.domain.event.service;

import gaji.service.domain.event.dto.request.EventInfoRequest;
import gaji.service.domain.event.dto.response.EventInfoListResponse;

import java.time.LocalDate;

public interface EventService {

    EventInfoListResponse getEventList(LocalDate date, Long userId);
    Long putEvent(LocalDate date, Long userId, Long myId, EventInfoRequest request);
    Long patchEvent(Long eventId, Long myId, EventInfoRequest request);
    Long deleteEvent(Long eventId, Long myId);
    Long putEventComplete(Long eventId, Long myId);
    Long deleteEventComplete(Long eventId, Long myId);
}
