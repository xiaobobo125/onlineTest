package com.bolife.online.controller;

import com.bolife.online.dto.AjaxResult;
import com.bolife.online.entity.Post;
import com.bolife.online.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
