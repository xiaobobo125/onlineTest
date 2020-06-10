package com.bolife.online.controller;

import com.bolife.online.dto.AjaxResult;
import com.bolife.online.entity.Account;
import com.bolife.online.entity.Grade;
import com.bolife.online.entity.Question;
import com.bolife.online.entity.Question_Contest;
import com.bolife.online.service.ContestService;
import com.bolife.online.service.GraderService;
import com.bolife.online.service.QuestionService;
import com.bolife.online.service.Question_ContentService;
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

    @Autowired
    private ContestService contestService;

    @Autowired
    private Question_ContentService question_contentService;
    @RequestMapping(value = "/api/submitContest",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult submitProblem(HttpServletRequest request, @RequestBody Grade grade){
        AjaxResult ajaxResult = new AjaxResult();
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        List<String> answer = Arrays.asList(grade.getAnswerJson().split(FinalDefine.SPLIT_CHAR));
        int autoResult = 0;
        logger.info("获取题库");
        List<Question_Contest> byContestId = question_contentService.getByContestId(grade.getContestId());
        List<Question> questionByContestId = questionService.getQuestionByIds(byContestId);
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
        Integer integer = graderService.addGrade(grade);
        return  ajaxResult.setData(integer);
    }

    //完成批改试卷
    @RequestMapping(value="/api/finishGrade", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult finishGrade(@RequestBody Grade grade) {
        AjaxResult ajaxResult = new AjaxResult();
        grade.setResult(grade.getAutoResult()+grade.getManulResult());
        grade.setFinishTime(new Date());
        grade.setState(1);
        boolean result = graderService.updateGrade(grade);
        contestService.updateContestStateById(grade.getContestId(),3);
        return new AjaxResult().setData(result);
    }
}
