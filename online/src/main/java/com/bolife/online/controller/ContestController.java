package com.bolife.online.controller;

import com.bolife.online.dto.AjaxResult;
import com.bolife.online.entity.*;
import com.bolife.online.exception.QexzWebError;
import com.bolife.online.service.ContestService;
import com.bolife.online.service.GraderService;
import com.bolife.online.service.QuestionService;
import com.bolife.online.service.Question_ContentService;
import com.bolife.online.util.FinalDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 16:13
 * @Description:
 */
@Controller
@RequestMapping("/contest")
public class ContestController extends BaseController {
    @Autowired
    private ContestService contestService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private GraderService graderService;

    @Autowired
    private Question_ContentService question_contentService;

    @RequestMapping(value = "/{contestId}",method = RequestMethod.GET)
    public String goContestDetail(HttpServletRequest request,
                                  @PathVariable("contestId") int contestId,
                                  Model model){
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        //TODO::拦截器过滤处理
        if (currentAccount == null) {
            //用户未登录直接返回首页面
            return "redirect:/";
        }
        Map<String, Object> data = new HashMap<>();
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        Contest contest = contestService.getContestById(contestId);
        if(contest == null
                || contest.getStartTime().getTime() > System.currentTimeMillis()
                    || contest.getEndTime().getTime() < System.currentTimeMillis()){
            return "redirect:/contest/index";
        }
        List<Question_Contest> byContestId = question_contentService.getByContestId(contestId);
        List<Question> questionByContestId = questionService.getQuestionByIds(byContestId);
        for (Question question : questionByContestId) {
            question.setAnswer("");
        }
        data.put("userStatus",0);
        data.put("contest", contest);
        data.put("questions", questionByContestId);
        model.addAttribute(FinalDefine.DATA, data);
        return "/contest/detail";
    }

    @RequestMapping(value="/api/addContest", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult addContest(@RequestBody Contest contest) {
        AjaxResult ajaxResult = new AjaxResult();
        contest.setUpdateTime(new Date());
        int contestId = contestService.addContest(contest);
        return new AjaxResult().setData(contestId);
    }

    //更新考试信息
    @RequestMapping(value="/api/updateContest", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateContest(@RequestBody Contest contest) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = contestService.updateContest(contest);
        return new AjaxResult().setData(result);
    }

    //删除考试信息
    @RequestMapping("/api/deleteContest/{id}")
    @ResponseBody
    public AjaxResult deleteContest(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = contestService.deleteContest(id);
        return new AjaxResult().setData(result);
    }

    //完成考试批改
    @RequestMapping(value="/api/finishContest/{id}", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult finishContest(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        Contest contest = contestService.getContestById(id);
        contest.setState(3);
        boolean result = contestService.updateContest(contest);
        return ajaxResult.setData(result);
    }


    @RequestMapping(value = "api/randomContest",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult randomContest(HttpServletRequest request){
        Integer type = 0;
        Integer soloSel = Integer.valueOf(request.getParameter("soloSel"));
        Integer manySel = Integer.valueOf(request.getParameter("manySel"));
        Integer queAns = Integer.valueOf(request.getParameter("queAns"));
        Integer program = Integer.valueOf(request.getParameter("program"));
        Integer sucErr = Integer.valueOf(request.getParameter("sucErr"));
        Integer space = Integer.valueOf(request.getParameter("space"));
        Integer contestId = Integer.valueOf(request.getParameter("contestId"));
        Integer subjectId = Integer.valueOf(request.getParameter("subjectId"));
        Integer scoreCount = contestService.getContestById(contestId).getTotalScore();
        List<Question> allQuestion = questionService.getQuestionBySubjectId(subjectId);
        Map<Integer,List<Question>> queCount = new HashMap<>();
        for (Question question : allQuestion) {
            if(!queCount.containsKey(question.getQuestionType())){
                queCount.put(question.getQuestionType(),new ArrayList<>());
            }else{
                List<Question> questions = queCount.get(question.getQuestionType());
                questions.add(question);
                queCount.put(question.getQuestionType(),questions);
            }
        }
        for (int i = 0; i < 6;i++){
            switch (i){
                case 0:
                    type = contestRandomQuestions(soloSel, queCount.get(i), contestId,i,scoreCount);
                    if (type == 0){
                        return AjaxResult.fixedError(QexzWebError.QUESTION_COUNT);
                    }
                    scoreCount += type;
                    break;
                case 1:
                    type = contestRandomQuestions(manySel, queCount.get(i), contestId,i,scoreCount);
                    if (type == 0){
                        return AjaxResult.fixedError(QexzWebError.QUESTION_COUNT);
                    }
                    scoreCount += type;
                    break;
                case 2:
                    type = contestRandomQuestions(queAns, queCount.get(i), contestId,i,scoreCount);
                    if (type == 0){
                        return AjaxResult.fixedError(QexzWebError.QUESTION_COUNT);
                    }
                    scoreCount += type;
                    break;
                case 3:
                    type = contestRandomQuestions(program, queCount.get(i), contestId,i,scoreCount);
                    if (type == 0){
                        return AjaxResult.fixedError(QexzWebError.QUESTION_COUNT);
                    }
                    scoreCount += type;
                    break;
                case 4:
                    type = contestRandomQuestions(space, queCount.get(i), contestId,i,scoreCount);
                    if (type == 0){
                        return AjaxResult.fixedError(QexzWebError.QUESTION_COUNT);
                    }
                    scoreCount += type;
                    break;
                case 5:
                    type = contestRandomQuestions(sucErr, queCount.get(i), contestId,i,scoreCount);
                    if (type == 0){
                        return AjaxResult.fixedError(QexzWebError.QUESTION_COUNT);
                    }
                    scoreCount += type;
                    break;
            }
        }
        contestService.updataTotalScore(contestId,scoreCount);
        return new AjaxResult().setData(type);
    }
}
