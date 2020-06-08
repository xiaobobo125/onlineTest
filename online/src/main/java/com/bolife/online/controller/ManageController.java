package com.bolife.online.controller;

import com.bolife.online.entity.Account;
import com.bolife.online.entity.Question;
import com.bolife.online.entity.Subject;
import com.bolife.online.service.ContestService;
import com.bolife.online.service.QuestionService;
import com.bolife.online.service.SubjectService;
import com.bolife.online.util.FinalDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/8 16:11
 * @Description:
 */
@Controller
@RequestMapping("/manage")
public class ManageController {
    @Autowired
    private ContestService contestService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private QuestionService questionService;


    @RequestMapping(value={"/login","/"}, method= RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);

        if (currentAccount == null) {
            return "/manage/manage-login";
        } else {
            return "redirect:/manage/contest/list";
        }
    }

    @RequestMapping(value="/contest/list", method= RequestMethod.GET)
    public String contestList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "/error/404";
        } else {
            Map<String, Object> data = contestService.getContests(page, FinalDefine.contestPageSize);
            List<Subject> subjects = subjectService.getAllSubjects();
            data.put("subjects", subjects);
            model.addAttribute(FinalDefine.DATA, data);
            return "/manage/manage-contestBoard";
        }
    }
    @RequestMapping(value="/question/list", method= RequestMethod.GET)
    public String questionList(HttpServletRequest request,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "content", defaultValue = "") String content,
                               Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "/error/404";
        } else {
            Map<String, Object> data = questionService.getQuestionsByContent(page,
                    FinalDefine.questionPageSize, content);
            List<Question> questions = (List<Question>) data.get("questions");
            List<Subject> subjects = subjectService.getAllSubjects();
            Map<Integer, String> subjectId2name = subjects.stream().
                    collect(Collectors.toMap(Subject::getId, Subject::getName));
            for (Question question : questions) {
                question.setSubjectName(subjectId2name.
                        getOrDefault(question.getSubjectId(), "未知科目"));
            }
            data.put("subjects", subjects);
            data.put("content", content);
            model.addAttribute("data", data);
            return "/manage/manage-questionBoard";
        }
    }
}
