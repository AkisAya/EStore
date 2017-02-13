package me.aki.estore.filter;

import me.aki.estore.domain.User;
import me.aki.estore.exception.UserException;
import me.aki.estore.factory.BasicFactory;
import me.aki.estore.service.UserService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Aki on 2017/2/11.
 */
public class AutoLoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 没有记录登录状态（session中没有用户时）才进行自动登录逻辑
        if (request.getSession(false) == null || request.getSession().getAttribute("user") == null) {
            // 获取自动登录cookie
            Cookie[] cookies = request.getCookies();
            Cookie autoLoginCookie = null;
            for (Cookie c: cookies) {
                if ("autoLogin".equals(c.getName())) {
                    autoLoginCookie = c;
                    break;
                }
            }

            if (autoLoginCookie != null) {
                // 获取用户名和密码并查询用户
                String value = autoLoginCookie.getValue();
                String username = value.split(":")[0];
                String password = value.split(":")[1];
                UserService userService = BasicFactory.getFactory().getService(UserService.class);
                try {
                    User user = userService.findUserByNameAndPwd(username, password);
                    if (user != null) {
                        // 用户存在则登录
                        request.getSession().setAttribute("user", user);
                    }
                } catch (UserException e) {
                    e.printStackTrace();
                }
            }
        }


        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
