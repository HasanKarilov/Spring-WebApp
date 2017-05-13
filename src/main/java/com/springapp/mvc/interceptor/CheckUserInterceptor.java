package com.springapp.mvc.interceptor;

import com.springapp.mvc.objects.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hanaria on 5/13/17.
 */
public class CheckUserInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if(request.getRequestURI().contains("check-user")){

            User user = (User) modelAndView.getModel().get("user");
            if(user==null || !user.isAdmin())
            {
                response.sendRedirect(request.getContextPath() + "/failed");
            }
        }
    }
}
