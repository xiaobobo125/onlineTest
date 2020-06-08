package com.bolife.online.controller;

import com.bolife.online.dto.AjaxResult;
import com.bolife.online.entity.Comment;
import com.bolife.online.service.CommenService;
import com.bolife.online.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 18:00
 * @Description:
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private PostService postService;

    @Autowired
    private CommenService commenService;

    @RequestMapping(value = "api/addComment",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addComment(@RequestBody Comment comment){
        AjaxResult ajaxResult = new AjaxResult();
        postService.updateReplyNumById(comment.getPostId());
        Integer integer = commenService.addComment(comment);
        return ajaxResult.setData(integer);
    }
}
