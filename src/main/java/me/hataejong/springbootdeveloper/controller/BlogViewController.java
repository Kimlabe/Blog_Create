package me.hataejong.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.hataejong.springbootdeveloper.domain.Article;
import me.hataejong.springbootdeveloper.dto.ArticleListViewResponse;
import me.hataejong.springbootdeveloper.dto.ArticleViewResponse;
import me.hataejong.springbootdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model){
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles", articles); // 블로그 글 리스트 저장
            // .addAttribute() 메서드를 사용하여 모델에 값을 저장
            // "articles"키에 블로그 글들(리스트)를 저장

        return "articleList"; // resource/templates/articleList.html 뷰 조회
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model){
                // └ 인자 id에 URI로 넘어온 값을 받아 finById()메서드로 넘겨
                // └ 글을 조회하고, 화면엣허 사용할 모델에 데이터를 저장한 다음,
                // └ 보여줄 화면의 템플릿 이름을 반환합니다.
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

    @GetMapping("/new-article")
    // id 키를 가진 쿼리 파라미터의 값을 id 변수에 매핑(id는 없을 수도 있음)
    public String newArticle(@RequestParam(required = false) Long id, Model model){
        if (id == null) { // id가 없으면 생성
            model.addAttribute("article", new ArticleViewResponse());
        }
        else {
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }
        return "newArticle";
    }
}

