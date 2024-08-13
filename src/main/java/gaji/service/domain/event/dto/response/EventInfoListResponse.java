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
}