package com.zhihu.intelligent.system.service;

import com.zhihu.intelligent.common.constants.SystemConstants;
import com.zhihu.intelligent.common.utils.LogUtil;
import com.zhihu.intelligent.common.utils.UserUtil;
import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.Log;
import com.zhihu.intelligent.system.entity.Topic;
import com.zhihu.intelligent.system.entity.UserTopic;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.exception.UserNameAlreadyExistException;
import com.zhihu.intelligent.security.model.RegisterUser;
import com.zhihu.intelligent.system.entity.User;
import com.zhihu.intelligent.system.repository.LogRepository;
import com.zhihu.intelligent.system.repository.TopicRepository;
import com.zhihu.intelligent.system.repository.UserRepository;
import com.zhihu.intelligent.system.repository.UserTopicRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

import static com.zhihu.intelligent.common.utils.UserUtil.getNullPropertyNames;

/**
 * @author wzt
 * 用户User服务类，对User表进行增删改查操作，以及分页查询
 */
@Service
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
        Optional<User> optionalUser = userRepository.findByUsername(registerUser.getUsername());
        if (optionalUser.isPresent()) {
            throw new UserNameAlreadyExistException("用户名已经存在");
        }
        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.getPassword()));
        user.setNickName(registerUser.getUsername());
        user.setAvatarUrl("https://avatars0.githubusercontent.com/u/58016945?s=400&v=4");
        user.setRoles("ROLE_USER,ROLE_ADMIN,ROLE_ROOT");
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
    public GlobalResponse getUserById(String id) {
        User user = userRepository.findById(id).get();
        return UserUtil.globalResponse(200, "获取用户资料成功！", user);
    }

    @Action(type = "UPDATE", operation = "更新用户信息")
    public GlobalResponse updateUser(User user) {
        User currentInstance = userRepository.findById(user.getId()).get();

        // 支持部分更新
        String[] nullPropertyNames = getNullPropertyNames(user);
        BeanUtils.copyProperties(user, currentInstance, nullPropertyNames);

        String modifiedID = UserUtil.getUserId();
        currentInstance.setModifiedID(modifiedID);
        currentInstance.setModifiedDate(new Date());
        userRepository.save(currentInstance);
        return UserUtil.globalResponse(200, "更新用户资料成功!", currentInstance);
    }


    @Action(type = "UPDATE", operation = "更新头像")
    public GlobalResponse updateAvatar(MultipartFile file, String userId) {
        String avatarUrl = imageService.executeUpload(file, SystemConstants.BASE_DIR, avatarPath, userId);
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
