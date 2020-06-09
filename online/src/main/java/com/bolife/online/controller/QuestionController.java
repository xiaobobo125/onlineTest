package com.bolife.online.controller;

import com.bolife.online.dto.AjaxResult;
import com.bolife.online.entity.Account;
import com.bolife.online.entity.Question;
import com.bolife.online.service.QuestionService;
import com.bolife.online.service.Question_ContentService;
import com.bolife.online.service.SubjectService;
import com.bolife.online.util.FinalDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 17:43
 * @Description:
 */
@Controller
@RequestMapping(value = "/question")
public class QuestionController extends BaseController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private Question_ContentService question_contentService;

    //添加题目
    @RequestMapping(value="/api/addQuestion", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult addQuestion(@RequestBody Question question) {
        AjaxResult ajaxResult = new AjaxResult();
        int questionId = questionService.addQuestion(question);
        return new AjaxResult().setData(questionId);
    }

    //删除题目信息
    @RequestMapping("/api/deleteQuestion/{id}")
    @ResponseBody
    public AjaxResult deleteContest(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        Boolean aBoolean = question_contentService.deleteQuestion(id);
        return new AjaxResult().setData(aBoolean);
    }

}
