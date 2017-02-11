package me.aki.estore.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Aki on 2017/2/10.
 */

public class EncodingFilter implements Filter {
    private ServletContext context;
    private FilterConfig filterConfig;
    private String encode;

    public void init(FilterConfig config) throws ServletException {
        this.context = config.getServletContext();
        this.filterConfig = config;
        this.encode = context.getInitParameter("encode");
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;

        // 请求乱码
        HttpServletRequest myRequest = new MyHttpServletRequest(request);

        // 响应乱码
        resp.setCharacterEncoding(encode);
        resp.setContentType("text/html;charset=" + encode);

        chain.doFilter(myRequest, resp);
    }



    private class MyHttpServletRequest extends HttpServletRequestWrapper {

        private HttpServletRequest request = null;
        private boolean hasEncode = false;

        public MyHttpServletRequest(HttpServletRequest request) {
            super(request);
            this.request = request;
        }


        @Override
        public String getParameter(String name) {
            String[] values = getParameterValues(name);
            if (values != null && values.length > 0) {
                return values[0];
            } else {
                return null;
            }
        }

        @Override
        public Map<String, String[]> getParameterMap() {

            try {

                if (request.getMethod().equalsIgnoreCase("post")) {
                    request.setCharacterEncoding(encode);
                    return request.getParameterMap();

                } else if (request.getMethod().equalsIgnoreCase("get")) {
                    Map<String, String[]> map = request.getParameterMap();
                    if (!hasEncode) {
                        for (Map.Entry<String, String[]> entry : map.entrySet()) {
                            String[] values = entry.getValue();
                            if (values != null && values.length > 0) {
                                for (String value: values) {
                                    value = new String(value.getBytes("iso8859-1"), encode);
                                }
                            }
                        }
                        hasEncode = true;
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return super.getParameterMap();
        }

        @Override
        public String[] getParameterValues(String name) {
            return getParameterMap().get(name);
        }
    }

}


