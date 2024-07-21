package gaji.service.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Files")
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //확장자
    private String type;

    //크기
    private Integer size;

    //원본이름
    private String originalName;

    //원본이름
    private String FileName;

    //파일 경로
    private String path;




}
