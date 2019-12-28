package com.zhihu.intelligent.system.service;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.zhihu.intelligent.common.constants.SystemConstants;
import com.zhihu.intelligent.common.utils.AuthUtil;
import com.zhihu.intelligent.common.utils.LogUtil;
import com.zhihu.intelligent.common.utils.UserUtil;
import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.Log;
import com.zhihu.intelligent.system.exception.FormatException;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.exception.UserNameAlreadyExistException;
import com.zhihu.intelligent.security.model.RegisterUser;
import com.zhihu.intelligent.system.entity.User;
import com.zhihu.intelligent.system.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

import static com.zhihu.intelligent.common.utils.UserUtil.getNullPropertyNames;

/**
 * @author wzt
 * 用户User服务类，对User表进行增删改查操作，以及分页查询
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private LogRepository logRepository;


    private static String avatarPath = "avatar/";

    public GlobalResponse saveUser(RegisterUser registerUser, HttpServletRequest request) {
        log.info(JSON.toJSONString(registerUser));
        Optional<User> optionalUser = userRepository.findByUsername(registerUser.getUsername());
        if (optionalUser.isPresent()) {
            throw new UserNameAlreadyExistException("用户名已经存在");
        }
        if (!AuthUtil.isRegister(registerUser)) {
            throw new FormatException("数据格式有误");
        }
        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.getPassword()));
        user.setNickName(registerUser.getUsername());
        user.setAvatarUrl("https://avatars0.githubusercontent.com/u/58016945?s=400&v=4");
        user.setRoles("ROLE_USER");
        user.setPermissions("CREATE,READ,UPDATE,DELETE");
        user.setStatus(1);
        user.setCreateDate(new Date());
        user.setIndustry(registerUser.getIndustry());
        user.setCareer(registerUser.getCareer());
        user.setEducation(registerUser.getEducation());
        user.setGender(registerUser.getGender());
        user.setEmail(registerUser.getEmail());
        user = userRepository.save(user);
        Log log = LogUtil.createLog(request, "CREATE", "用户注册", null);
        log.setUserId(user.getId());
        logRepository.save(log);
        return new GlobalResponse(200, "注册成功");
    }


    public User findUserByUserName(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("没有改用户名:" + username));
    }

    @Action(type = "READ", operation = "获取用户信息")
    @Cacheable(cacheNames = "user", key = "#id")
    public User getUserById(String id) {
        User user = userRepository.findById(id).get();
        log.info(id + ":" + "获取用户信息:" + JSON.toJSONString(user));
        return user;
    }

    @Action(type = "UPDATE", operation = "更新用户信息")
    @CachePut(cacheNames = "user",key = "#user.id")
    public User updateUser(User user) {
        log.info(JSON.toJSONString(user));
        log.info("@CachePut 更新用户信息");
        User currentInstance = userRepository.findById(user.getId()).get();

        // 支持部分更新
        String[] nullPropertyNames = getNullPropertyNames(user);
        BeanUtils.copyProperties(user, currentInstance, nullPropertyNames);

        String modifiedID = UserUtil.getUserId();
        currentInstance.setModifiedID(modifiedID);
        currentInstance.setModifiedDate(new Date());
        userRepository.save(currentInstance);
        return currentInstance;
    }


    @Action(type = "UPDATE", operation = "更新头像")
    public GlobalResponse updateAvatar(MultipartFile file, String userId) {
        if (file == null) {
            return new GlobalResponse(1002, "文件不存在");
        }
        String avatarUrl = imageService.executeUpload(file, userId);
        User user = userRepository.findById(userId).get();
        user.setAvatarUrl(avatarUrl);
        userRepository.save(user);
        GlobalResponse globalResponse = new GlobalResponse(201, "上传头像成功");
        globalResponse.getData().put("avatarUrl", avatarUrl);
        return globalResponse;
    }

    public void deleteUserByUserName(String username) {
        userRepository.deleteByUsername(username);
    }


    public Page<User> getAllUser(int pageNum, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNum, pageSize));
    }
}
