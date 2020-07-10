package com.yhq.api.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: YHQ
 * @Date: 2020/6/2 23:39
 */
//@Component
public class CORSFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 100;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();

        HttpServletResponse response = ctx.getResponse();
        // 设置哪个源可以访问我
        response.setHeader("Access-Control-Allow-Origin", "manage.taobao.com");
        // 允许哪个方法(也就是哪种类型的请求)访问我
        response.setHeader("Access-Control-Allow-Methods", "PUT,GET,POST,OPTIONS");
        // 允许携带哪个头访问我
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-File-Name,token");
        //文本大小，可以不设置（下载文件的时候注意下，发现没加的话，文件大小变了）
        if (ctx.getOriginContentLength() != null && ctx.getOriginContentLength() > 0) {
            response.setHeader("Content-Length", ctx.getOriginContentLength().toString());
        }
        //允许携带cookie
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ctx.setResponse(response);

        return null;
    }
}
