package com.zhihu.intelligent.system.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhihu.intelligent.common.constants.SystemConstants;
import com.zhihu.intelligent.common.utils.UserUtil;
import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.exception.UnknownUserUpdateException;
import com.zhihu.intelligent.system.exception.UserNameAlreadyExistException;
import com.zhihu.intelligent.security.model.RegisterUser;
import com.zhihu.intelligent.system.entity.User;
import com.zhihu.intelligent.system.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    private static String avtarPath = "avtar/";

    public User saveUser(RegisterUser registerUser) {
        Optional<User> optionalUser = userRepository.findByUsername(registerUser.getUsername());
        if (optionalUser.isPresent()) {
            throw new UserNameAlreadyExistException("User name already exist!Please choose another user name.");
        }
        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.getPassword()));
        user.setNickName(registerUser.getUsername());
        user.setAvtarUrl("https://avatars0.githubusercontent.com/u/58016945?s=400&v=4");
        user.setRoles("USER");
        user.setPermissions("CREATE,READ,UPDATE,DELETE");
        user.setStatus(1);
        user.setCreateDate(new Date());
        user.setIndustry(registerUser.getIndustry());
        user.setCareer(registerUser.getCareer());
        user.setEducation(registerUser.getEducation());
        user.setGender(registerUser.getGender());
        user.setEmail(registerUser.getEmail());
        return userRepository.save(user);
    }


    public User findUserByUserName(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
    }

    @Action(type = "READ",operation = "获取用户信息")
    public String getUserById(String id){
        User user = userRepository.findById(id).get();
        return getUserInfo(200, "获取用户资料成功！", user);
    }

    @Action(type = "UPDATE",operation = "更新用户信息")
    public String updateUser(User user) {
        User currentInstance = userRepository.findById(user.getId()).get();

        //支持部分更新
        String[] nullPropertyNames = getNullPropertyNames(user);
        BeanUtils.copyProperties(user, currentInstance, nullPropertyNames);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new UnknownUserUpdateException("Reject an unknown user to edit user information.");
        }

        String modifiedUserName = (String) authentication.getPrincipal();

        User modifiedUser = userRepository.findByUsername(modifiedUserName).get();
        if (modifiedUser == null){
            return JSON.toJSONString(new GlobalResponse(400,"Not Found"));
        }
        String modifiedID = modifiedUser.getId();
        currentInstance.setModifiedID(modifiedID);
        currentInstance.setModifiedDate(new Date());
        userRepository.save(currentInstance);
        return getUserInfo(200, "更新用户资料成功!", currentInstance);
    }

    public String getUserInfo(int code, String message, User user) {
        GlobalResponse globalResponse = new GlobalResponse(code, message);
        Map<String,Object> data = new HashMap<>();
        data.put("username", user.getUsername());
        data.put("nickname", user.getNickName());
        data.put("description", user.getDescription());//
        data.put("industry", user.getIndustry());
        data.put("career", user.getCareer());
        data.put("education", user.getEducation());
        data.put("name", user.getName());//
        data.put("gender", user.getGender());
        data.put("birthday", user.getBirthday());//
        data.put("phone", user.getPhone());//
        data.put("email", user.getEmail());
        data.put("qq", user.getQq());//
        globalResponse.setData(new JSONObject(data));
        return globalResponse.toString();
    }

    @Action(type = "UPDATE",operation = "更新头像")
    public String updateAvtar(MultipartFile file){
        String avtarUrl = imageService.executeUpload(file, SystemConstants.BASE_DIR,avtarPath);
        User user = userRepository.findByUsername(UserUtil.getUsername()).get();
        user.setAvtarUrl(avtarUrl);
        userRepository.save(user);
        GlobalResponse globalResponse = new GlobalResponse(200,"上传头像成功");
        globalResponse.getData().put("avtarUrl",avtarUrl);
        return globalResponse.toString();
    }

    public void deleteUserByUserName(String username) {
        userRepository.deleteByUsername(username);
    }


    public Page<User> getAllUser(int pageNum, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNum, pageSize));
    }

}
