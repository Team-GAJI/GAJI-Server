package gaji.service.domain.event.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventInfoListResponse {
    private List<EventInfo> eventInfoList;
    private List<StudyEventInfo> studyEventInfoList;


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventInfo {
        private Long eventId;
        private String description;
        private String startDate;
        private String endDate;
        private boolean completionStatus;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StudyEventInfo {
        private Long eventId;
        private String description;
        private String StudyName;
        private String startDate;
        private String endDate;
        private boolean completionStatus;
    }

}