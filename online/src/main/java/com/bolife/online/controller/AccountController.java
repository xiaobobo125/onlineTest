package com.bolife.online.controller;

import com.bolife.online.dto.AjaxResult;
import com.bolife.online.entity.Account;
import com.bolife.online.entity.Contest;
import com.bolife.online.entity.Grade;
import com.bolife.online.entity.Subject;
import com.bolife.online.exception.QexzWebError;
import com.bolife.online.service.*;
import com.bolife.online.util.FinalDefine;
import com.bolife.online.util.MD5;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/7 10:24
 * @Description:
 */
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController{

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostService postService;

    @Autowired
    private GraderService graderService;

    @Autowired
    private ContestService contestService;

    @Autowired
    private SubjectService subjectService;


    /***
     * 用户登陆验证
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/login",method = RequestMethod.POST)
    public AjaxResult login(HttpServletRequest request, HttpServletResponse response){
        AjaxResult ajaxResult = new AjaxResult();
        try {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        logger.info("根据用户名获取用户信息："+username);
        Account account = accountService.getAccountByUsername(username);
        if (account != null){
            String pwd = MD5.md5(FinalDefine.MD5_SALT+password);
            if (password.equals(account.getPassword()) || pwd.equals(account.getPassword())){
                request.getSession().setAttribute(FinalDefine.CURRENT_ACCOUNT,account);
                ajaxResult.setData(account);
            }else {
                return AjaxResult.fixedError(QexzWebError.WRONG_PASSWORD);
            }
        }else{
            return AjaxResult.fixedError(QexzWebError.WRONG_USERNAME);
        }
        }catch (Exception e){
            return AjaxResult.fixedError(QexzWebError.COMMON);
        }
        return  ajaxResult;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().setAttribute(FinalDefine.CURRENT_ACCOUNT,null);
        String url=request.getHeader("Referer");
        return "redirect:"+url;
    }

    @RequestMapping(value = "api/updateAccount",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateAccount(HttpServletRequest request, HttpServletResponse response) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            String phone = request.getParameter("phone");
            String qq = request.getParameter("qq");
            String email = request.getParameter("email");
            String description = request.getParameter("description");
            String avatarImgUrl = request.getParameter("avatarImgUrl");

            Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
            currentAccount.setPhone(phone);
            currentAccount.setQq(qq);
            currentAccount.setEmail(email);
            currentAccount.setDescription(description);
            currentAccount.setAvatarImgUrl(avatarImgUrl);
            boolean result = accountService.updateAccount(currentAccount);
            ajaxResult.setSuccess(result);
        } catch (Exception e) {
            return AjaxResult.fixedError(QexzWebError.COMMON);
        }
        return  ajaxResult;
    }

    @RequestMapping("/profile")
    public String goProfile(HttpServletRequest request, Model model){
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        //TODO::拦截器过滤处理
        if (currentAccount == null) {
            //用户未登录直接返回首页面
            return "redirect:/";
        }
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        return "/user/profile";
    }

    @RequestMapping(value="/password", method= RequestMethod.GET)
    public String password(HttpServletRequest request, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        //TODO::拦截器过滤处理
        if (currentAccount == null) {
            //用户未登录直接返回首页面
            return "redirect:/";
        }
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        return "/user/password";
    }

    @RequestMapping(value = "api/updatePassword",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updatePassword(HttpServletRequest request,HttpServletResponse responset){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmNewPassword = request.getParameter("confirmNewPassword");
            String md5OldPassword = MD5.md5(FinalDefine.MD5_SALT+oldPassword);
            String md5NewPassword = MD5.md5(FinalDefine.MD5_SALT+newPassword);
            if (StringUtils.isNotEmpty(newPassword) && StringUtils.isNotEmpty(confirmNewPassword)
                    && !newPassword.equals(confirmNewPassword)) {
                return AjaxResult.fixedError(QexzWebError.NOT_EQUALS_CONFIRM_PASSWORD);
            }
            Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
            if (!currentAccount.getPassword().equals(md5OldPassword) && !currentAccount.getPassword().equals(newPassword)) {
                return AjaxResult.fixedError(QexzWebError.WRONG_PASSWORD);
            }
            currentAccount.setPassword(md5NewPassword);
            boolean result = accountService.updateAccount(currentAccount);
            ajaxResult.setSuccess(result);
        } catch (Exception e) {
            return AjaxResult.fixedError(QexzWebError.COMMON);
        }
        return  ajaxResult;
    }


    @RequestMapping(value="/myExam", method= RequestMethod.GET)
    public String myExam(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        //TODO::拦截器过滤处理
        if (currentAccount == null) {
            //用户未登录直接返回首页面
            return "redirect:/";
        }
        Map<String, Object> data = graderService.getGradesByStudentId(page, FinalDefine.gradePageSize, currentAccount.getId());
        List<Grade> grades = (List<Grade>) data.get("grades");
        List<Contest> contests = contestService.getAllContests();
        List<Subject> subjects = subjectService.getAllSubjects();
        Map<Integer, String> subjectId2name = subjects.stream().
                collect(Collectors.toMap(Subject::getId, Subject::getName));
        for (Contest contest : contests) {
            contest.setSubjectName(subjectId2name.
                    getOrDefault(contest.getSubjectId(), "未知科目"));
        }
        Map<Integer, Contest> id2contest = contests.stream().
                collect(Collectors.toMap(Contest::getId, contest -> contest));
        for (Grade grade : grades) {
            grade.setContest(id2contest.get(grade.getContestId()));
        }
        model.addAttribute(FinalDefine.DATA, data);
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        return "/user/myExam";
    }

    @RequestMapping(value="/myDiscussPost", method= RequestMethod.GET)
    public String myDiscussPost(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        //TODO::拦截器过滤处理
        if (currentAccount == null) {
            //用户未登录直接返回首页面
            return "redirect:/";
        }
        Map<String, Object> data = postService.getPostsByAuthorId(page, FinalDefine.postPageSize, currentAccount.getId());
        model.addAttribute(FinalDefine.DATA, data);
        model.addAttribute(FinalDefine.CURRENT_ACCOUNT, currentAccount);
        return "/user/myDiscussPost";
    }

    @RequestMapping(value = "/api/uploadAvatar", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadAvatar(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //原始名称
            String oldFileName = file.getOriginalFilename(); //获取上传文件的原名
            //存储图片的物理路径
            String file_path = FinalDefine.UPLOAD_FILE_IMAGE_PATH;
            //上传图片
            if(file!=null && oldFileName!=null && oldFileName.length()>0){
                //新的图片名称
                String newFileName = UUID.randomUUID() + oldFileName.substring(oldFileName.lastIndexOf("."));
                //新图片
                File newFile = new File(file_path+newFileName);
                //将内存中的数据写入磁盘
                file.transferTo(newFile);
                //将新图片名称返回到前端
                ajaxResult.setData(newFileName);
            }else{
                return AjaxResult.fixedError(QexzWebError.UPLOAD_FILE_IMAGE_NOT_QUALIFIED);
            }
        } catch (Exception e) {
            return AjaxResult.fixedError(QexzWebError.UPLOAD_FILE_IMAGE_ANALYZE_ERROR);
        }
        return ajaxResult;
    }
}
