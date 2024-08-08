package gaji.service.domain.event.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
    private List<EventInfo> eventInfoList;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventInfo {
        private UUID eventId;
        private String description;
        private String startDate;
        private String endDate;
        private boolean commitmentStatus;
    }
}