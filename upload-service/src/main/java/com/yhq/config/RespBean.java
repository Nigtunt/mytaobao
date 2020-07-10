package com.yhq.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @Author: YHQ
 * @Date: 2020/6/24 16:54
 */
@Data
public class RespBean {
    private String status;
    private String msg;
    private Object o;

    public static RespBean error(String error){
        RespBean re = new RespBean();
        re.msg=error;
        re.status="500";
        return re;
    }
    public static RespBean ok(String error,Object o){
        RespBean re = new RespBean();
        re.msg=error;
        re.status="200";
        re.o=o;
        return re;
    }
    public static RespBean ok(String error){
        RespBean re = new RespBean();
        re.msg=error;
        re.status="200";
        return re;
    }
}
