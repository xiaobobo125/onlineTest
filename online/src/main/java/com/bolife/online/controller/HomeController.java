package com.bolife.online.controller;

import com.bolife.online.dto.AjaxResult;
import com.bolife.online.entity.Account;
import com.bolife.online.entity.Post;
import com.bolife.online.service.AccountService;
import com.bolife.online.service.ContestService;
import com.bolife.online.service.PostService;
import com.bolife.online.service.SubjectService;
import com.bolife.online.util.FinalDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 14:51
 * @Description:
 */
@Controller
public class HomeController extends BaseController {
    @Autowired
    private ContestService contestService;
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostService postService;
    @RequestMapping("/contest/index")
    public String getContestIndex(HttpServletRequest request,
                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                  Model model) {
        logger.info("在session中获取用户信息");
        Account user = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT);
        logger.info("获取试题信息");
        Map<String, Object> contests = contestService.getContests(page, FinalDefine.contestPageSize);
        model.addAttribute(FinalDefine.DATA,contests);
        return  "contest/index";
    }

    @RequestMapping("/problemset/list")
    public String getProblemList(HttpServletRequest request,
                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                 Model model){
        logger.info("在session中获取用户信息");
        Account user = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT);
        logger.info("获取题目列表");
        Map<String, Object> subjects = subjectService.getSubjects(page, FinalDefine.contestPageSize);
        model.addAttribute(FinalDefine.DATA,subjects);
        return "/problem/problemset";
    }

    @RequestMapping("/discuss")
    public String goDisscuss(HttpServletRequest request,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             Model model){
        logger.info("在session中获取用户信息");
        Account user = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT);
        Map<String, Object> posts = postService.getPosts(page, FinalDefine.contestPageSize);
        List<Post> postList = (List<Post>) posts.get("posts");
        List<Account> allAccount = accountService.getAllAccount();
        Map<Integer, Account> id2author = allAccount.stream().
                collect(Collectors.toMap(Account::getId, account -> account));
        for (Post post : postList) {
            post.setAuthor(id2author.get(post.getAuthorId()));
        }
        model.addAttribute(FinalDefine.DATA, posts);
        return "/discuss/discuss";
    }

    @RequestMapping("/")
    public String goHome(HttpServletRequest request,
                         Model model){
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        return "home";
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult time() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return new AjaxResult().setData(localDateTime);
    }

}
