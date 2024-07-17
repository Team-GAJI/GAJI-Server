package gaji.service.domain.psot;

import gaji.service.domain.User;
import gaji.service.domain.enums.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type")
    private PostType postType;

    private String title;
    private String body;
    private int views;
    private int likes;
    private int bookmarks;

    @Enumerated(EnumType.STRING)
    private Status status;
}
