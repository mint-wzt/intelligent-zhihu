package com.zhihu.intelligent.system.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhihu.intelligent.common.constants.SecurityConstants;
import com.zhihu.intelligent.common.response.GlobalResponse;
import com.zhihu.intelligent.common.utils.JwtTokenUtils;
import com.zhihu.intelligent.system.exception.UnknownUserUpdateException;
import com.zhihu.intelligent.system.exception.UserNameAlreadyExistException;
import com.zhihu.intelligent.security.entity.RegisterUser;
import com.zhihu.intelligent.system.entity.User;
import com.zhihu.intelligent.system.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveUser(RegisterUser registerUser) {
        Optional<User> optionalUser = userRepository.findByUsername(registerUser.getUsername());
        if (optionalUser.isPresent()) {
            throw new UserNameAlreadyExistException("User name already exist!Please choose another user name.");
        }
        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.getPassword()));
        user.setNickName(registerUser.getUsername());
        user.setAvtarUrl("avtar_url");
        user.setRoles("USER");
        user.setPermissions("CREATE,READ,UPDATE,DELETE");
        user.setStatus(1);
        user.setCreateDate(new Date());
        user.setIndustry(registerUser.getIndustry());
        user.setCareer(registerUser.getCareer());
        user.setEducation(registerUser.getEducation());
        user.setGender(registerUser.getGender());
        user.setEmail(registerUser.getEmail());
        userRepository.save(user);
    }

    public User findUserByUserName(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
    }

    public void deleteUserByUserName(String username) {
        userRepository.deleteByUsername(username);
    }


    public Page<User> getAllUser(int pageNum, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNum, pageSize));
    }

    public String getUser(String id) {
        User user = userRepository.findById(id).get();
        return getUser(200, "获取用户资料成功！", user);
    }

    public String updateUser(String id, User user) {
        User currentInstance = userRepository.findById(id).get();

        //支持部分更新
        String[] nullPropertyNames = getNullPropertyNames(user);
        BeanUtils.copyProperties(user, currentInstance, nullPropertyNames);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new UnknownUserUpdateException("Reject an unknown user to edit user information.");
        }
//
//        String modifiedUserName = (String) authentication.getPrincipal();
//
//        User user1 = userRepository.findByUsername(modifiedUserName).get();
//        if (user1 == null){
//            return JSON.toJSONString(new GlobalResponse(400,"Not Found"));
//        }
//        String modifiedID = user1.getId();
//        currentInstance.setModifiedID(modifiedID);
//        currentInstance.setModifiedDate(new Date());
        userRepository.save(currentInstance);
        return getUser(200, "更新用户资料成功!", currentInstance);
    }

    public String getUser(int code, String message, User user) {
        GlobalResponse globalResponse = new GlobalResponse(code, message);
        JSONObject data = globalResponse.getData();
        data.put("username", user.getUsername());
        data.put("nickname", user.getNickName());
        data.put("description", user.getDescription());
        data.put("industry", user.getIndustry());
        data.put("career", user.getCareer());
        data.put("education", user.getEducation());
        data.put("name", user.getName());
        data.put("gender", user.getName());
        data.put("birthday", user.getBirthday());
        data.put("phone", user.getPhone());
        data.put("email", user.getEmail());
        data.put("qq", user.getQq());
//        globalResponse.setData(data);
        return globalResponse.toString();
    }

}
