package io.vicp.frlib.web.mvc.handler;

import io.vicp.frlib.web.exception.BusinessException;
import io.vicp.frlib.web.mvc.model.User;
import io.vicp.frlib.web.mvc.model.UserInfo;
import io.vicp.frlib.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by zhoudr on 2016/12/27.
 */
public class BusinessExceptionHandler implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(BusinessExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = new UserInfo();
        User user = new User();
        user.setName("张三");
        user.setId(1);
        userInfo.setUser(user);
        Date date = null;
        try {
            date = DateUtil.convertStringDateWithDefaultNow(null);
        } catch (ParseException e) {
            logger.warn("", e);
        }
        userInfo.setLoginTime(date);
        mv.addObject("userInfo", userInfo);
        if (ex instanceof BusinessException) {
            mv.setViewName("common/exception");
            mv.addObject("exceptionMsg", ((BusinessException) ex).getMsg());
        } else if (ex instanceof NullPointerException) {
            mv.setViewName("common/exception");
            mv.addObject("exceptionMsg", "请联系管理员");
        } else {
            mv.setViewName("redirect:index.do");
        }
        return mv;
    }
}
