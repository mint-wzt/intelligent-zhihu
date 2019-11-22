package com.zhihu.intelligent.system.aop;

import com.zhihu.intelligent.common.constants.SecurityConstants;
import com.zhihu.intelligent.common.utils.JwtTokenUtils;
import com.zhihu.intelligent.common.utils.LogUtil;
import com.zhihu.intelligent.system.entity.Log;
import com.zhihu.intelligent.system.entity.User;
import com.zhihu.intelligent.system.service.LogService;
import io.jsonwebtoken.Claims;
import org.apache.tomcat.util.net.IPv6Utils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class UserLogAspect {

    @Autowired
    private LogService logService;


    @Pointcut("execution(* com.zhihu.intelligent.system.service.UserService.saveUser(..))")
    public void registerPointCut() {
    }

    @Pointcut("@annotation(com.zhihu.intelligent.system.aop.Action)")
    public void pointCut() { }

    /**
     * 处理用户注册的日志情况
     * @param res
     */
    @AfterReturning(value = "registerPointCut()", returning = "res")
    public void afterRegister(Object res) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User user = (User) res;
        Log log = LogUtil.createLog(request, "CREATE", "用户注册",null);
        log.setUserId(user.getId());
        log.setUsername(user.getUsername());
        logService.save(log);
    }

    /**
     * 将日志写入数据库
     * @param joinPoint
     */
    @After(value = "pointCut()")
    public void after(JoinPoint joinPoint){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorization = request.getHeader(SecurityConstants.TOKEN_HEADER);
        String token = authorization.replace(SecurityConstants.TOKEN_PREFIX,"");
        Claims claims = JwtTokenUtils.getTokenBody(token);
        String id = claims.getId(); // 获取用户id
        String username = claims.getSubject(); // 获取用户名
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Action action = method.getAnnotation(Action.class); // 获取操作类型 和 操作内容
        Log log = LogUtil.createLog(request,action.type(),action.operation(),null); // 获取客户端IP地址，构造log对象
        log.setUserId(id);
        log.setUsername(username);
        logService.save(log);
    }

}
