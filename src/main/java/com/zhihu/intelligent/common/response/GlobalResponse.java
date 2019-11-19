package com.zhihu.intelligent.common.response;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class GlobalResponse implements Serializable {
    //状态码
    private Integer code;

    //状态描述
    private String message;

    //业务数据
    private JSONObject data;


    public GlobalResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = new JSONObject();
    }


    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("message", message);
        jsonObject.put("data", data);
        return jsonObject.toJSONString();
    }


}
