package com.angus.orderBeats.filter;

import com.alibaba.fastjson.JSON;
import com.angus.orderBeats.common.R;
import com.angus.orderBeats.common.baseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
// check the user is already login or not. if not, filter the management page
    public static final AntPathMatcher PATH_MATCHER= new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)  servletRequest;
        HttpServletResponse res=(HttpServletResponse) servletResponse;
        log.info("request filter: {}", req.getRequestURI());

        String requestURI = req.getRequestURI();
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/frontend/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        //check URI
        boolean checkURI=check(urls, requestURI);

        if (checkURI) {
            filterChain.doFilter(req, res);
            return;
        }
        //check user status is login or not
        if(req.getSession().getAttribute("employee")!=null){
            Long employeeId = (Long) req.getSession().getAttribute("employee");
            baseContext.setCurrentId(employeeId); //set current user's id to thread, and get it later in MyMetaObjecthandler
            filterChain.doFilter(req, res);
            return;
        }
        //if not login, send msg to frontend
        res.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

    }
    //check url includes in urls or not
    public boolean check(String[] urls, String requestURI) {
        for (String url: urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
