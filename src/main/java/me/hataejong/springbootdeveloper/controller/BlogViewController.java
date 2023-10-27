package me.hataejong.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.hataejong.springbootdeveloper.dto.ArticleListViewResponse;
import me.hataejong.springbootdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
