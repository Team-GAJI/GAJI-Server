package gaji.service.domain.event.service;

import gaji.service.domain.event.dto.request.EventInfoRequest;
import gaji.service.domain.event.dto.response.EventInfoListResponse;
import org.joda.time.DateTime;

public interface EventService {

    public EventInfoListResponse getEventList(DateTime date, Long userId);
    public Long putEvent(DateTime date, Long userId, EventInfoRequest request);
    public Long patchEvent(Long eventId, EventInfoRequest request);
    public Long deleteEvent(Long eventId);
    public Long putEventComplete(Long eventId);
    public Long deleteEventComplete(Long eventId);
}
