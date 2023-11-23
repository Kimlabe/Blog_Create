package me.hataejong.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.hataejong.springbootdeveloper.domain.Article;
import me.hataejong.springbootdeveloper.dto.AddArticleRequest;
import me.hataejong.springbootdeveloper.dto.ArticleResponse;
import me.hataejong.springbootdeveloper.dto.UpdateArticleRequest;
import me.hataejong.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class BlogApiController {

    private final BlogService blogService;

    // HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/articles")  /* '/api/articles'은 addArticle() 메서드에 매핑 */
    // @RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal){
        Article savedArticle = blogService.save(request, principal.getName());

    // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED) /* - 201 Created */
                .body(savedArticle);
    }

    /* 응답 코드 리스트*/
    /*
    * 200 OK : 요청이 성공적으로 수행되었음.
    * 201 Created : 요청이 성공적으로 수행되었꼬, 새로운 리소스가 생성되었음.
    * 400 Bad Request : 요청 값이 잘못되어 요청에 실패하였음.
    * 403 Forbidden : 권한이 없어 요청에 실패했음.
    * 404 Not Found : 요청 값으로 찾은 리소스가 없어 요청에 실패했음.
    * 500 Internal Server Error : 서버 상에 문제가 있어 요청에실패했음.
    */

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/api/articles/{id}")
    // URL경로에서 값 추출
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        Article article = blogService.findById(id);    // └URL에서 값을 가져오는 애너테이션

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id){
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request){
        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}
