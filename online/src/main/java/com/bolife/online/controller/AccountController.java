package com.bolife.online.controller;

import com.bolife.online.dto.AjaxResult;
import com.bolife.online.entity.Account;
import com.bolife.online.exception.QexzWebError;
import com.bolife.online.service.AccountService;
import com.bolife.online.util.FinalDefine;
import com.bolife.online.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
}
