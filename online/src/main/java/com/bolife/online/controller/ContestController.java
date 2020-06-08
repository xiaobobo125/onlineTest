package com.bolife.online.controller;

import com.bolife.online.entity.Account;
import com.bolife.online.entity.Contest;
import com.bolife.online.entity.Grade;
import com.bolife.online.entity.Question;
import com.bolife.online.service.ContestService;
import com.bolife.online.service.GraderService;
import com.bolife.online.service.QuestionService;
import com.bolife.online.util.FinalDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Question> questionByContestId = questionService.getQuestionByContestId(contestId);
        for (Question question : questionByContestId) {
            question.setAnswer("");
        }
        data.put("userStatus",0);
        data.put("contest", contest);
        data.put("questions", questionByContestId);
        model.addAttribute(FinalDefine.DATA, data);
        return "/contest/detail";
    }
}
