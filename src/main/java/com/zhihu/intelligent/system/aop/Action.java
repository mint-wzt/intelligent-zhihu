package com.zhihu.intelligent.system.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {
    String type();
    String operation();
    String remark() default "";
}
