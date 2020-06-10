package com.bolife.online.controller;

import com.bolife.online.dto.AjaxResult;
import com.bolife.online.entity.Subject;
import com.bolife.online.service.SubjectService;
import com.bolife.online.util.FinalDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/10 13:13
 * @Description:
 */
@Controller
@RequestMapping("/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    //添加课程
    @RequestMapping(value="/api/addSubject", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult addSubject(@RequestBody Subject subject) {
        AjaxResult ajaxResult = new AjaxResult();
        subject.setImgUrl(FinalDefine.DEFAULT_SUBJECT_IMG_URL);
        subject.setQuestionNum(0);
        subject.setUpdateTime(new Date());
        int subjectId = subjectService.addSubject(subject);
        return new AjaxResult().setData(subjectId);
    }

    //更新课程
    @RequestMapping(value="/api/updateSubject", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateSubject(@RequestBody Subject subject) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = subjectService.updateSubject(subject);
        return new AjaxResult().setData(result);
    }

    //删除课程
    @RequestMapping("/api/deleteSubject/{id}")
    @ResponseBody
    public AjaxResult deleteSubject(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = subjectService.deleteSubjectById(id);
        return new AjaxResult().setData(result);
    }
}
