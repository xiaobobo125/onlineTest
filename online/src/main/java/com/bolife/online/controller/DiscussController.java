package com.bolife.online.controller;

import com.bolife.online.dto.AjaxResult;
import com.bolife.online.entity.*;
import com.bolife.online.service.*;
import com.bolife.online.util.FinalDefine;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/7 16:03
 * @Description:
 */
@Controller
@RequestMapping("/discuss")
public class DiscussController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommenService commenService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private GoodService goodService;

    @RequestMapping(value = "/{postId}",method = RequestMethod.GET)
    public String discussDetail(HttpServletRequest request, @PathVariable("postId") Integer postId, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT,currentAccount);
        Map<String, Object> data = new HashMap<>();
        Post post = postService.getPostById(postId);
        Account author = accountService.getAccountById(post.getAuthorId());
        post.setAuthor(author);
        data.put("post",post);
        Integer flag = 0;
        if(post.getGoodNum() > 0 && currentAccount != null){
            List<Goods> goodsByPostId = goodService.getGoodsByPostId(postId);
            for (Goods goods : goodsByPostId) {
                if(goods.getAccountId() == currentAccount.getId()){
                    flag = 1;
                    break;
                }
            }
        }
        List<Comment> comments = commenService.getCommentByPostId(postId);
        List<Reply> replies = replyService.getReplyByPostId(postId);
        List<Account> allAccount = accountService.getAllAccount();
        Map<Integer, Account> users = allAccount.stream().
                collect(Collectors.toMap(Account::getId, account -> account));
        for (Comment comment : comments) {
            comment.setUser(users.get(comment.getUserId()));
        }
        for (Reply reply : replies) {
            reply.setUser(users.get(reply.getUserId()));
            if (reply.getAtuserId() != 0) {
                reply.setAtuser(users.get(reply.getAtuserId()));
            }
        }
        Map<Integer, Comment> id2comment = comments.stream().
                collect(Collectors.toMap(Comment::getId, comment -> comment));
        for (Reply reply : replies) {
            Comment comment = id2comment.get(reply.getCommentId());
            if(comment != null){
                if( comment.getReplies() == null){
                    comment.setReplies(new ArrayList<>());
                }
                comment.getReplies().add(reply);
            }
        }
        data.put("comments", comments);
        if (currentAccount != null){
            data.put("userId", currentAccount.getId());
        } else {
            data.put("userId", 0);
        }
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(FinalDefine.DATA, data);
        model.addAttribute("goodState",flag);
        return "/discuss/discussDetail";
    }
    @RequestMapping(value="/post", method= RequestMethod.GET)
    public String postDiscuss(HttpServletRequest request, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        if(currentAccount == null){
            return "redirect:/404";
        }
        Map<String, Object> data = new HashMap<>();
        if(currentAccount != null){
            data.put("authorId", currentAccount.getId());
        }else{
            data.put("authorId", null);
        }
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(FinalDefine.DATA, data);
        return "/discuss/postDiscuss";
    }
    @RequestMapping("/delete/{pid}")
    public String goUpdate(HttpServletRequest request,
                           @PathVariable("pid")int pid,
                           Model model){
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT,currentAccount);
        postService.deletePost(pid);
        return "redirect:/discuss";
    }

    @RequestMapping(value = "/api/addGood",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addGood(@RequestBody Goods goods){
        Integer count = goodService.addGood(goods);
        Post postById = postService.getPostById(goods.getPostId());
        postService.updateGoodNumById(postById.getGoodNum()+1,postById.getId());
        return new AjaxResult().setData(postById);
    }

    @RequestMapping(value = "/api/subGood",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult subGood(@RequestBody Goods goods){
        Integer count = goodService.subGood(goods);
        Post postById = postService.getPostById(goods.getPostId());
        postService.updateGoodNumById(postById.getGoodNum()-1,postById.getId());
        return new AjaxResult().setData(postById);
    }
}
