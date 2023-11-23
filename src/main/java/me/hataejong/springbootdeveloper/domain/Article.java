package me.hataejong.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity // 엔티티로 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id // Id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false) // 'title'이라는 not null 컬럼과 매핑
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder // 빌더 패턴으로 객체 생성
    public Article(String author, String title, String content){
        this.author = author;
        this.title = title;
        this.content = content;
    }
    /*
    // Getter
    public Long getId() {
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getContent() {
        return content;
    }
    */

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    @CreatedDate // 엔티티가 생성될때 생성 시간 저장
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될때 수정 시간 저장
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "author", nullable = false)
    private String author;

}
