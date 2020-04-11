package com.lagou.controller;

import com.lagou.pojo.Article;
import com.lagou.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private HttpServletRequest httpServletRequest;

    private int currentPageIndex = 0;

    @RequestMapping("/index")
    public String toIndexPage(Model model) {

        final List<Article> all = articleRepository.findAll();
        model.addAttribute("articles", all);
        return "client/index";
    }

    @RequestMapping("/article")
    public String toArticlePage(Model model) {
        final List<Article> all = articleRepository.findAll();
        final int totalPage = all.size();

        final String page = httpServletRequest.getParameter("page");


        switch (page) {
            case "first":
                currentPageIndex = 0;
                break;

            case "prev":
                if (currentPageIndex > 0) {
                    currentPageIndex--;
                }
                break;

            case "next":
                if (currentPageIndex < totalPage - 1) {
                    currentPageIndex++;
                }
                break;

            case "last":
                currentPageIndex = totalPage - 1;
                break;

            default:
                break;
        }

        Pageable currentPage = PageRequest.of(currentPageIndex, 1);
        final Page<Article> article = articleRepository.findAll(currentPage);
        model.addAttribute("articles", article);
        return "client/index";
    }


}
