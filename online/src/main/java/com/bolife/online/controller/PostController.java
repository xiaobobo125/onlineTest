package com.bolife.online.controller;

import com.bolife.online.dto.AjaxResult;
import com.bolife.online.entity.Post;
import com.bolife.online.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/7 17:10
 * @Description:
 */
@Controller
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @RequestMapping(value = "/api/addPost",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addPost(@RequestBody Post post){
        AjaxResult ajaxResult = new AjaxResult();
        post.setLastReplyTime(new Date());
        post.setUpdateTime(new Date());
        Integer integer = postService.addPost(post);
        return ajaxResult.setData(integer);
    }
    //删除帖子
    @RequestMapping("/api/deletePost/{id}")
    public AjaxResult deletePost(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = postService.deletePostById(id);
        return new AjaxResult().setData(result);
    }
}
