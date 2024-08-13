package gaji.service.domain.event.mapper;

import gaji.service.domain.event.domain.Event;
import gaji.service.domain.event.dto.response.EventInfoListResponse;
import gaji.service.global.converter.DateConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    public EventInfoListResponse toEventInfoListResponse(List<Event> events) {
        return EventInfoListResponse.builder()
                .eventInfoList(events.stream()
                        .map(event -> EventInfoListResponse.EventInfo.builder()
                                .eventId(event.getId())
                                .description(event.getContent())
                                .startDate(DateConverter.convertToRelativeTimeFormat(event.getStartDateTime()))
                                .endDate(DateConverter.convertToRelativeTimeFormat(event.getEndDateTime()))
                                .completionStatus(event.getIsCompleted())
                                .build()
                        )
                        .collect(Collectors.toList()))
                .build();
    }
}
