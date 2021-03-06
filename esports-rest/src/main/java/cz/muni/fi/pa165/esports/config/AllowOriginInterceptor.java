package cz.muni.fi.pa165.esports.config;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * We need to rewrite headers to allow for cross-origin access for some
 * operations e.g. DELETE. Without this, you might get messages such as "HTTP
 * Status 405 - Request method 'DELETE' not supported" when trying to DELETE,
 * PUT or POST Here we are allowing all origins "Access-Control-Allow-Origin",
 * "*" and all operations "Access-Control-Allow-Methods", "GET, POST, PUT,
 * DELETE, OPTIONS"
 *
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
public class AllowOriginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {
        String origin = request.getHeader("Origin");

        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, *");
        return true;
    }

}
