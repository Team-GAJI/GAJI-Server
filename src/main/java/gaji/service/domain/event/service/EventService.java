package gaji.service.domain.event.service;

import gaji.service.domain.event.dto.request.EventInfoRequest;
import gaji.service.domain.event.dto.response.EventInfoListResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface EventService {

    public EventInfoListResponse getEventList(LocalDate date, Long userId);
    public Long putEvent(LocalDateTime date, Long userId, Long myId, EventInfoRequest request);
    public Long patchEvent(Long eventId, Long myId, EventInfoRequest request);
    public Long deleteEvent(Long eventId, Long myId);
    public Long putEventComplete(Long eventId, Long myId);
    public Long deleteEventComplete(Long eventId, Long myId);
}
