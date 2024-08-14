package gaji.service.domain.roomBoard.entity;

import gaji.service.domain.enums.UserAlarmTypeEnum;
import gaji.service.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class TroublePostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private RoomTroublePost roomTroublePost;

    private String body;
    @Enumerated(EnumType.STRING)
    private UserAlarmTypeEnum status;

    public void updateComment(String body){
        this.body = body;
    }

    public boolean isAuthor(Long userId) {
        return this.user.getId().equals(userId);
    }
}
