package gaji.service.domain.room.web.dto;

import gaji.service.domain.studyMate.Assignment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDto {
    private Long id;
    private Integer weeks;
    private String body;

    public RoomResponseDto(Assignment assignment) {
        this.id = assignment.getId();
        this.weeks = assignment.getWeeks();
        this.body = assignment.getBody();
    }
}
