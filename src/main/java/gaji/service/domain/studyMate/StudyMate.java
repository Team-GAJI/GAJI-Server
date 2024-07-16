package gaji.service.domain.studyMate;


import gaji.service.domain.User;
import gaji.service.domain.Post.RoomComment;
import gaji.service.domain.room.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyMate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    Room room;

    @OneToMany(mappedBy = "studyMate", cascade = CascadeType.ALL)
    private List<RoomComment> roomCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessageList = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<ChatUser> chatUserList = new ArrayList<>();

    @OneToMany(mappedBy = "studyMate", cascade = CascadeType.ALL)
    private List<UserAssignment> userAssignmentLIst = new ArrayList<>();
}
