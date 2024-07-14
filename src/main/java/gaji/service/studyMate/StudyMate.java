package gaji.service.studyMate;


import gaji.service.domain.User;
import gaji.service.domain.roomPost.RoomComment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class StudyMate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "studyMate", cascade = CascadeType.ALL)
    private List<RoomComment> roomCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "studyMate", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessageList = new ArrayList<>();

    @OneToMany(mappedBy = "studyMate", cascade = CascadeType.ALL)
    private List<ChatUser> chatUserList = new ArrayList<>();


}
