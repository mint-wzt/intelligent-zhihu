package com.zhihu.intelligent.system.aop;

import com.zhihu.intelligent.common.utils.LogUtil;
import com.zhihu.intelligent.common.utils.UserUtil;
import com.zhihu.intelligent.system.entity.Log;
import com.zhihu.intelligent.system.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
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

    @Pointcut("@annotation(com.zhihu.intelligent.system.aop.Action)")
    public void pointCut() { }

    /**
     * 将日志写入数据库
     * @param joinPoint
     */
    @After(value = "pointCut()")
    public void after(JoinPoint joinPoint){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String id = UserUtil.getUserId(); // 获取当前用户ID
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Action action = method.getAnnotation(Action.class); // 获取操作类型 和 操作内容
        Log log = LogUtil.createLog(request,action.type(),action.operation(),null); // 获取客户端IP地址，构造log对象
        log.setUserId(id);
        logService.save(log);
    }

}
