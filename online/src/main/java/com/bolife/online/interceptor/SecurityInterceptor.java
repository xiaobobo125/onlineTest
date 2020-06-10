package com.bolife.online.interceptor;

import com.bolife.online.entity.Account;
import com.bolife.online.util.FinalDefine;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/10 15:43
 * @Description:
 */
@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Account account = (Account) request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        if (account == null || account.getLevel() < 1) {
            response.sendRedirect("/404");
            return false;
        }
        return true;
    }
}
