package com.yhq.common.execption;

import lombok.*;
import org.springframework.http.HttpStatus;

/**
 * @Author: YHQ
 * @Date: 2020/5/30 12:15
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum  ExecptionEnum {
    //价格为空
    NULL_PRICE_EXECPTION(HttpStatus.INTERNAL_SERVER_ERROR.value(),"价格不能为空！"),
    //传入商品parent_id不能为空
    PID_CANT_NULL(HttpStatus.BAD_REQUEST.value(),"参数parent_id不正确"),
    CATEGORY_NULL(HttpStatus.BAD_REQUEST.value(),"category参数错误"),
    RESULT_NULL(205,"结果集为空"),
    ID_NULL(HttpStatus.BAD_REQUEST.value(),"id不能为空"),
    UPLOAD_EXECPTION(HttpStatus.BAD_REQUEST.value(),"上传文件异常"),
    OTHER_EXECPTION();
    //


    private int statusCode;
    private String msg;

}
