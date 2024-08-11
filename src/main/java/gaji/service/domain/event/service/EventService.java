package gaji.service.domain.event.service;

import gaji.service.domain.event.domain.Event;
import gaji.service.domain.event.dto.request.EventInfoRequest;
import gaji.service.domain.event.dto.response.EventInfoListResponse;
import org.joda.time.DateTime;

public interface EventService {

    public EventInfoListResponse getEventList(DateTime date, Long userId);
    public Long putEvent(DateTime date, Long userId, Long myId, EventInfoRequest request);
    public Long patchEvent(Long eventId, Long myId, EventInfoRequest request);
    public Long deleteEvent(Long eventId, Long myId);
    public Long putEventComplete(Long eventId, Long myId);
    public Long deleteEventComplete(Long eventId, Long myId);
}
