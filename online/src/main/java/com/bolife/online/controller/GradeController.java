package com.bolife.online.controller;

import com.bolife.online.dto.AjaxResult;
import com.bolife.online.entity.Account;
import com.bolife.online.entity.Grade;
import com.bolife.online.entity.Question;
import com.bolife.online.service.GraderService;
import com.bolife.online.service.QuestionService;
import com.bolife.online.util.FinalDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/7 13:15
 * @Description:
 */
@Controller
@RequestMapping("/grade")
public class GradeController extends BaseController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private GraderService graderService;

    @RequestMapping(value = "/api/submitContest",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult submitProblem(HttpServletRequest request, @RequestBody Grade grade){
        AjaxResult ajaxResult = new AjaxResult();
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        List<String> answer = Arrays.asList(grade.getAnswerJson().split(FinalDefine.SPLIT_CHAR));
        int autoResult = 0;
        logger.info("获取题库");
        List<Question> questionByContestId = questionService.getQuestionByContestId(grade.getContestId());
        for(int i = 0 ;i<questionByContestId.size();i++){
            Question question = questionByContestId.get(i);
            String ans = "";
            if (answer.size() >= i+1){
                ans = answer.get(i);
            }else{
                break;
            }
            if(question.getQuestionType() <= 1 && question.getAnswer().equals(ans)){
                autoResult += 1;
            }
        }
        grade.setStudentId(currentAccount.getId());
        grade.setResult(autoResult);
        grade.setAutoResult(autoResult);
        grade.setManulResult(0);
        grade.setFinishTime(new Date());
        logger.info("添加成绩");
        Grade stuGrade = graderService.getGradeByConIdAndStuId(grade.getContestId(), grade.getStudentId());
        Integer integer = graderService.addGrade(grade);
        return  ajaxResult.setData(integer);
    }
}
