package gaji.service.domain.event.mapper;

import gaji.service.domain.event.domain.Event;
import gaji.service.domain.event.dto.response.EventInfoListResponse;
import gaji.service.domain.room.entity.Room;
import gaji.service.domain.room.entity.RoomEvent;
import gaji.service.domain.studyMate.entity.Assignment;
import gaji.service.global.converter.DateConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    //Event를 EventInfoListResponse.EventInfo List로 변환
    public List<EventInfoListResponse.EventInfo> toEventInfoList(List<Event> events) {
        return events.stream().map(
                event -> EventInfoListResponse.EventInfo.builder()
                        .eventId(event.getId())
                        .description(event.getContent())
                        .startDate(DateConverter.convertToTimeFormat(event.getStartDateTime()))
                        .endDate(DateConverter.convertToTimeFormat(event.getEndDateTime()))
                        .build()
        ).collect(Collectors.toList());
    }

    //RoomEvent를 EventInfoListResponse.StudyEventInfo List로 변환
    public List<EventInfoListResponse.StudyEventInfo> toStudyEventInfoList(RoomEvent roomEvent, Room room) {
        return roomEvent.getAssignmentList().stream().map(
                assignment ->
                        EventInfoListResponse.StudyEventInfo.builder()
                                .eventId(assignment.getId())
                                .description(assignment.getBody())
                                .startDate(DateConverter.convertWriteTimeFormat(roomEvent.getStartTime(), ""))
                                .endDate(DateConverter.convertWriteTimeFormat(roomEvent.getEndTime(), ""))
                                .StudyName(room.getName())
                                .build()
        ).collect(Collectors.toList());
    }
}
