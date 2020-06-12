package com.bolife.online.controller;

import com.bolife.online.dto.AjaxResult;
import com.bolife.online.entity.Reply;
import com.bolife.online.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/8 14:32
 * @Description:
 */
@Controller
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @RequestMapping(value = "/api/addReply",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addReply(@RequestBody Reply reply){
        AjaxResult ajaxResult = new AjaxResult();
        int replyId = replyService.addReply(reply);
        return new AjaxResult().setData(replyId);
    }
}
