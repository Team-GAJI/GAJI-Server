package gaji.service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //확장자
    private String type;

    //크기
    private Integer size;

    //원본이름
    private String originalName;

    //파일 경로
    private String path;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL)
    private List<RoomPostImage> roomPostImageList = new ArrayList<>();




}
