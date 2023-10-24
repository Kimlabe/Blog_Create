package me.hataejong.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.hataejong.springbootdeveloper.domain.Article;
import me.hataejong.springbootdeveloper.dto.AddArticleRequest;
import me.hataejong.springbootdeveloper.dto.UpdateArticleRequest;
import me.hataejong.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // bin으로 등록
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    // 블로그 글 하나를 조회하는 메서드
    public Article findById(long id){
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    // 블로그 글을 삭제하는 메서드
    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    // 블로그 글을 수정하는 메서드
    @Transactional // 트랜잭션 메서드
    // └매칭한 메서드를 하나의 트랜잭션으로 묶는 역할
    /*트랜잭션이란
    * 데이터베이스의 데이터를 바꾸기 위해 묶는 작업의 단위
    * 두가지 이상의 작업을 하나의 작업 단위(트랜잭션)으로 묶어서 두 작업을 한 단위로 실행.
    * 만약 중간에 실패하게 된다면 트랜잭션의 처음 상태로 모두 되돌리면 된다.
    */
    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}