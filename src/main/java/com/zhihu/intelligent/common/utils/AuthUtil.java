package com.zhihu.intelligent.common.utils;

import com.zhihu.intelligent.security.model.RegisterUser;
import org.springframework.util.StringUtils;

/**
 * 处理用户登录、注册收据格式的工具类
 */
public class AuthUtil {

    /**
     * 用户注册数据格式验证
     * @param registerUser
     * @return
     */
    public static boolean isRegister(RegisterUser registerUser) {
        try {

            String username = registerUser.getUsername().trim();
            String password = registerUser.getPassword().trim();
            String industry = registerUser.getIndustry().trim();
            String career = registerUser.getCareer().trim();
            String education = registerUser.getEducation().trim();
            String gender = registerUser.getGender().trim();
            String email = registerUser.getEmail().trim();
            if (StringUtils.isEmpty(username) ||
                    StringUtils.isEmpty(password) ||
                    StringUtils.isEmpty(industry) ||
                    StringUtils.isEmpty(career) ||
                    StringUtils.isEmpty(education) ||
                    StringUtils.isEmpty(gender) ||
                    StringUtils.isEmpty(email)||
                    username.indexOf(" ") != -1 ||
                    password.indexOf(" ") != -1 ||
                    !email.contains("@")) {
                return false;
        }
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
