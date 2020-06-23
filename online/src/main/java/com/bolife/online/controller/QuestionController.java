package com.bolife.online.controller;

import com.bolife.online.dto.AjaxResult;
import com.bolife.online.entity.*;
import com.bolife.online.exception.QexzWebError;
import com.bolife.online.service.QuestionService;
import com.bolife.online.service.Question_ContentService;
import com.bolife.online.service.SubjectService;
import com.bolife.online.util.FinalDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @Autowired
    private SubjectService subjectService;

    //添加题目
    @RequestMapping(value="/api/addQuestion", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult addQuestion(@RequestBody Question question) {
        AjaxResult ajaxResult = new AjaxResult();
        int questionId = questionService.addQuestion(question);
        return new AjaxResult().setData(questionId);
    }

    //更新题目信息
    @RequestMapping(value="/api/updateQuestion", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateQuestion(@RequestBody Question question) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = questionService.updateQuestion(question);
        return new AjaxResult().setData(result);
    }

    //删除题目信息
    @RequestMapping("/api/deleteQuestion_content/{id}")
    @ResponseBody
    public AjaxResult deleteQuestion_content(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        Boolean aBoolean = question_contentService.deleteQuestion(id);
        return new AjaxResult().setData(aBoolean);
    }
    //删除题目信息
    @RequestMapping("/api/deleteQuestion/{id}")
    @ResponseBody
    public AjaxResult deleteContest(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        Boolean aBoolean = questionService.deleteQuestion(id);
        return new AjaxResult().setData(aBoolean);
    }

    @RequestMapping(value = "/api/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadAvatar(HttpServletRequest request,
                                           @RequestParam("file") MultipartFile file,
                                           @RequestParam("subjectId") Integer subjectId)
            throws IllegalStateException, IOException {
        AjaxResult ajaxResult = new AjaxResult();
        Integer integer = 0;
        try {
            //原始名称
            String oldFileName = file.getOriginalFilename(); //获取上传文件的原名
            //上传图片
            if(file!=null && oldFileName!=null && oldFileName.length()>0){
                integer = questionService.insertManyFile(file.getInputStream(), oldFileName, subjectId);
            }else{
                return AjaxResult.fixedError(QexzWebError.UPLOAD_FILE_IMAGE_NOT_QUALIFIED);
            }
        } catch (Exception e) {
            return AjaxResult.fixedError(QexzWebError.UPLOAD_FILE_IMAGE_ANALYZE_ERROR);
        }
        return ajaxResult.setData(integer);
    }

    @RequestMapping("/test/{subjectId}")
    public String goTest(HttpServletRequest request,
                         @PathVariable("subjectId") int subjectId,
                         Model model){
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        //TODO::拦截器过滤处理
        if (currentAccount == null) {
            //用户未登录直接返回首页面
            return "redirect:/";
        }
        Map<String, Object> data = new HashMap<>();
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        List<Question> questionBySubjectId = questionService.getQuestionBySubjectId(subjectId);
        Subject subjectById = subjectService.getSubjectById(subjectId);
        data.put("contest", subjectById);
        data.put("questions", questionBySubjectId);
        model.addAttribute(FinalDefine.DATA, data);
        return "problem/proConDetail";
    }

}
