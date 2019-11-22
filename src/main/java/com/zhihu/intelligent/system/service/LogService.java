package com.zhihu.intelligent.system.service;

import com.zhihu.intelligent.common.utils.LogUtil;
import com.zhihu.intelligent.system.entity.Log;
import com.zhihu.intelligent.system.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public void save(Log log){
        logRepository.save(log);
    }



}
