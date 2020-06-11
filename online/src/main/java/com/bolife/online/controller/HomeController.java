package com.bolife.online.controller;

import com.bolife.online.dto.AjaxResult;
import com.bolife.online.entity.*;
import com.bolife.online.service.*;
import com.bolife.online.util.FinalDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 14:51
 * @Description:
 */
@Controller
public class HomeController extends BaseController {
    @Autowired
    private ContestService contestService;
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostService postService;

    @Autowired
    private GraderService graderService;

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/contest/index")
    public String getContestIndex(HttpServletRequest request,
                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                  Model model) {
        logger.info("在session中获取用户信息");
        Account user = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT,user);
        logger.info("获取试题信息");
        Map<String, Object> contests = contestService.getContests(page, FinalDefine.contestPageSize);
        List<Contest> contestList = (List<Contest>) contests.get("contests");
        logger.info("获取用户成绩");
        for (Contest contest : contestList) {
            Date startTime = contest.getStartTime();
            long time = startTime.getTime();
            long endDate = contest.getEndTime().getTime();
            long nowDate = new Date().getTime();

            if(time > nowDate ){
                contestService.updateContestStateById(contest.getId(),0);
                contest.setState(0);
            }
            if(time <= nowDate && nowDate < endDate){
                contestService.updateContestStateById(contest.getId(),1);
                contest.setState(1);
            }
            if(endDate <= nowDate && contest.getState() == 1){
                contestService.updateContestStateById(contest.getId(),2);
                contest.setState(2);
            }
            if(user != null) {
                Grade gradeByConIdAndStuId = graderService.getGradeByConIdAndStuId(contest.getId(), user.getId());
                if (gradeByConIdAndStuId != null) {
                    System.out.println(gradeByConIdAndStuId);
                    contest.setUserState(1);
                } else {
                    contest.setUserState(0);
                }
            }
        }
        contests.put("contests",contestList);
        model.addAttribute(FinalDefine.DATA,contests);
        return  "contest/index";
    }

    @RequestMapping("/problemset/list")
    public String getProblemList(HttpServletRequest request,
                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                 Model model){
        logger.info("在session中获取用户信息");
        Account user = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT,user);
        logger.info("获取题目列表");
        Map<String, Object> subjects = subjectService.getSubjects(page, FinalDefine.contestPageSize);
        List<Subject> subjectList = (List<Subject>) subjects.get("subjects");
        for (Subject subject : subjectList) {
            Integer countQuestionBySubject = questionService.getCountQuestionBySubject(subject.getId());
            subject.setQuestionNum(countQuestionBySubject);
        }
        subjects.put("subjects",subjectList);
        model.addAttribute(FinalDefine.DATA,subjects);
        return "/problem/problemset";
    }

    @RequestMapping("/discuss")
    public String goDisscuss(HttpServletRequest request,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             Model model){
        logger.info("在session中获取用户信息");
        Account user = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT,user);
        Map<String, Object> posts = postService.getPosts(page, FinalDefine.contestPageSize);
        List<Post> postList = (List<Post>) posts.get("posts");
        List<Account> allAccount = accountService.getAllAccount();
        Map<Integer, Account> id2author = allAccount.stream().
                collect(Collectors.toMap(Account::getId, account -> account));
        for (Post post : postList) {
            post.setAuthor(id2author.get(post.getAuthorId()));
        }
        model.addAttribute(FinalDefine.DATA, posts);
        return "/discuss/discuss";
    }

    @RequestMapping("/")
    public String goHome(HttpServletRequest request,
                         Model model){
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        return "home";
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult time() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return new AjaxResult().setData(localDateTime);
    }

    @RequestMapping(value="/problemset/{problemsetId}/problems", method= RequestMethod.GET)
    public String problemList(HttpServletRequest request,
                              @PathVariable("problemsetId") Integer problemsetId,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "content", defaultValue = "") String content,
                              @RequestParam(value = "difficulty", defaultValue = "0") int difficulty,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        if(currentAccount == null){
            return "redirect: /404";
        }
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        Map<String,Object> data = questionService.getQuestionByPCD(page,FinalDefine.questionPageSize,problemsetId,content,difficulty);
        Subject subject = subjectService.getSubjectById(problemsetId);
        data.put("subject", subject);
        model.addAttribute(FinalDefine.DATA, data);
        return "/problem/problemlist";
    }

    @RequestMapping(value="/problemset/{problemsetId}/problem/{problemId}", method= RequestMethod.GET)
    public String problemDetail(HttpServletRequest request,
                                @PathVariable("problemsetId") Integer problemsetId,
                                @PathVariable("problemId") Integer problemId,
                                Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        Map<String, Object> data = new HashMap<>();
        Question question = questionService.getQuestionById(problemId);
        Subject subject = subjectService.getSubjectById(problemsetId);
        data.put("question", question);
        data.put("subject", subject);
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(FinalDefine.DATA, data);
        return "/problem/problemdetail";
    }

    @RequestMapping("/404")
    public String NotFound(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message);
        return "error/404";
    }
}
