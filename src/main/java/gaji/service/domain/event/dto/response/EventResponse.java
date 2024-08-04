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

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventInfo {
        private UUID eventId;
        private String title;
        private String description;
        private String startDate;
        private String endDate;
        private String createdAt;
        private String updatedAt;
        private List<UUID> tags;
    }
}